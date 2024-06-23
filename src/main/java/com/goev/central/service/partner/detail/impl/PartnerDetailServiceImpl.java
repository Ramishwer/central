package com.goev.central.service.partner.detail.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dao.business.BusinessSegmentDao;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.dto.auth.AuthUserDto;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDetailDto;
import com.goev.central.enums.DocumentStatus;
import com.goev.central.enums.partner.PartnerOnboardingStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.event.events.partner.update.PartnerUpdateEvent;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.repository.partner.detail.PartnerDetailRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.auth.AuthService;
import com.goev.central.service.partner.detail.PartnerDetailService;
import com.goev.central.service.partner.document.PartnerDocumentService;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.S3Utils;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.exceptions.ResponseException;
import com.goev.lib.utilities.ApplicationContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerDetailServiceImpl implements PartnerDetailService {

    private final PartnerRepository partnerRepository;
    private final PartnerDetailRepository partnerDetailRepository;
    private final LocationRepository locationRepository;
    private final BusinessClientRepository businessClientRepository;
    private final BusinessSegmentRepository businessSegmentRepository;
    private final AuthService authService;
    private final EventExecutorUtils eventExecutor;

    private final S3Utils s3;

    private final PartnerDocumentService partnerDocumentService;

    @Override
    public PartnerDetailDto createPartner(PartnerDetailDto partnerDto) {

        if (partnerDto.getAadhaarCardNumber() == null)
            throw new ResponseException("No aadhaar card number present");


        PartnerDetailDao existingPartnerDetail = partnerDetailRepository.findByAadhaarCardNumber(partnerDto.getAadhaarCardNumber());

        if (existingPartnerDetail != null) {
            throw new ResponseException("Error in saving partner: Partner with Aadhaar :" + partnerDto.getAadhaarCardNumber() + " already exist");
        }


        PartnerDao existingPartnerDao = partnerRepository.findByPhoneNumber(partnerDto.getPartner().getPhoneNumber());

        if (existingPartnerDao != null) {
            throw new ResponseException("Error in saving partner: Partner with phone number :" + partnerDto.getPartner().getPhoneNumber() + " already exist");
        }
        PartnerDao partnerDao = new PartnerDao();

        partnerDao.setPunchId("GOEV-" + SecretGenerationUtils.getCode());
        partnerDao.setPhoneNumber(partnerDto.getPartner().getPhoneNumber());
        partnerDao.setAuthUuid(authService.createUser(AuthUserDto.builder()
                .phoneNumber(partnerDto.getPartner().getPhoneNumber())
                .organizationUUID(ApplicationContext.getOrganizationUUID())
                .clientUUID(ApplicationConstants.PARTNER_CLIENT_UUID)
                .build()));
        partnerDao.setStatus(PartnerStatus.OFF_DUTY.name());
        partnerDao.setSubStatus(PartnerStatus.ON_DUTY.name());
        PartnerDao partner = partnerRepository.save(partnerDao);

        if (partner == null)
            throw new ResponseException("Error in saving details");

        PartnerDetailDao partnerDetailDao = getPartnerDetailDao(partnerDto);
        partnerDetailDao.setPartnerId(partner.getId());
        partnerDetailDao = partnerDetailRepository.save(partnerDetailDao);
        if (partnerDetailDao == null)
            throw new ResponseException("Error in saving partner details");

        partner.setPartnerDetailsId(partnerDetailDao.getId());
        partner.setProfileUrl(partnerDetailDao.getProfileUrl());
        partner.setOnboardingStatus(getOnboardingStatus(partnerDao, partnerDetailDao));


        partner.setViewInfo(ApplicationConstants.GSON.toJson(getPartnerViewDto(partnerDetailDao, partner)));
        partnerRepository.update(partner);

        return getPartnerDetailDto(partnerDetailDao, partner);


    }

    private PartnerViewDto getPartnerViewDto(PartnerDetailDao partnerDetails, PartnerDao partnerDao) {
        PartnerViewDto result = PartnerViewDto.builder()
                .firstName(partnerDetails.getFirstName())
                .lastName(partnerDetails.getLastName())
                .punchId(partnerDao.getPunchId())
                .state(partnerDao.getOnboardingStatus())
                .profileUrl(partnerDao.getProfileUrl())
                .phoneNumber(partnerDao.getPhoneNumber())
                .onboardingDate(partnerDetails.getOnboardingDate())
                .build();
        if (partnerDetails.getHomeLocationId() != null) {
            LocationDao locationDao = locationRepository.findById(partnerDetails.getHomeLocationId());
            if (locationDao != null) {
                result.setHomeLocation(LocationDto.builder().uuid(locationDao.getUuid()).name(locationDao.getName()).build());
            }
        }
        return result;
    }

    private PartnerDetailDto getPartnerDetailDto(PartnerDetailDao partnerDetails, PartnerDao partner) {
        PartnerDetailDto result = PartnerDetailDto.builder().build();
        setPartnerDetails(result, partner, partnerDetails);
        setPartnerHomeLocation(result, partnerDetails.getHomeLocationId());
        setBusinessSegment(result, partnerDetails.getBusinessSegmentId());
        setBusinessClient(result, partnerDetails.getBusinessClientId());
        return result;
    }


    @Override
    public PartnerDetailDto getPartnerDetails(String partnerUUID) {
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUUID);
        if (partnerDao == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDetailDao partnerDetailDao = partnerDetailRepository.findById(partnerDao.getPartnerDetailsId());

        if (partnerDetailDao == null)
            throw new ResponseException("No partner details found for Id :" + partnerUUID);


        return getPartnerDetailDto(partnerDetailDao, partnerDao);
    }

    @Override
    public PartnerDetailDto updatePartner(String partnerUUID, PartnerDetailDto partnerDetailDto) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);
        PartnerDetailDao partnerDetailDao = partnerDetailRepository.findById(partner.getPartnerDetailsId());

        if (partnerDetailDao == null)
            throw new ResponseException("No partner details found for Id :" + partnerUUID);

        PartnerDetailDao partnerDetails = getPartnerDetailDao(partnerDetailDto);
        partnerDetails.setPartnerId(partner.getId());
        partnerDetails.setAadhaarCardNumber(partnerDetailDao.getAadhaarCardNumber());
        partnerDetails = partnerDetailRepository.save(partnerDetails);
        if (partnerDetails == null)
            throw new ResponseException("Error in saving partner details");

        partner.setProfileUrl(partnerDetails.getProfileUrl());
        partner.setPartnerDetailsId(partnerDetails.getId());
        partner.setOnboardingStatus(getOnboardingStatus(partner, partnerDetailDao));

        partner.setViewInfo(ApplicationConstants.GSON.toJson(getPartnerViewDto(partnerDetails, partner)));
        partnerRepository.update(partner);

        return getPartnerDetailDto(partnerDetailDao, partner);
    }

    @Override
    public  boolean updateOnboardingStatus(String partnerUUID){
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);
        PartnerDetailDao partnerDetailDao = partnerDetailRepository.findById(partner.getPartnerDetailsId());

        if (partnerDetailDao == null)
            throw new ResponseException("No partner details found for Id :" + partnerUUID);

        String status = getOnboardingStatus(partner, partnerDetailDao);
        if(!status.equals(partner.getOnboardingStatus())) {
            partner.setOnboardingStatus(status);
            partner = partnerRepository.update(partner);
            eventExecutor.fireEvent(PartnerUpdateEvent.class.getName(),partner);
        }
        return true;
    }

    private String getOnboardingStatus(PartnerDao partner, PartnerDetailDao partnerDetailDao) {

        if (partner.getProfileUrl() == null)
            return PartnerOnboardingStatus.PENDING.name();

        if (partnerDetailDao.getHomeLocationId() == null)
            return PartnerOnboardingStatus.PENDING.name();

        if (partnerDetailDao.getFirstName() == null)
            return PartnerOnboardingStatus.PENDING.name();
        if (partnerDetailDao.getLastName() == null)
            return PartnerOnboardingStatus.PENDING.name();

        DocumentStatus documentStatus = partnerDocumentService.isAllMandatoryDocumentsUploaded(partner.getId());

        if (DocumentStatus.PENDING.equals(documentStatus) || DocumentStatus.REJECTED.equals(documentStatus))
            return PartnerOnboardingStatus.PENDING.name();

        if (DocumentStatus.UPLOADED.equals(documentStatus))
            return PartnerOnboardingStatus.PENDING_APPROVAL.name();

        if (DocumentStatus.APPROVED.equals(documentStatus))
            return PartnerOnboardingStatus.ONBOARDED.name();
        return PartnerOnboardingStatus.PENDING.name();
    }


    private PartnerDetailDao getPartnerDetailDao(PartnerDetailDto partnerDto) {
        PartnerDetailDao newPartnerDetails = new PartnerDetailDao();

        newPartnerDetails.setFirstName(partnerDto.getFirstName());
        newPartnerDetails.setLastName(partnerDto.getLastName());
        newPartnerDetails.setEmail(partnerDto.getEmail());
        newPartnerDetails.setAadhaarCardNumber(partnerDto.getAadhaarCardNumber());
        newPartnerDetails.setProfileUrl(partnerDto.getProfileUrl());
        newPartnerDetails.setLocalAddress(partnerDto.getLocalAddress());
        newPartnerDetails.setPermanentAddress(partnerDto.getPermanentAddress());
        newPartnerDetails.setFathersName(partnerDto.getFathersName());


        if (partnerDto.getBusinessClient() != null) {

            BusinessClientDao businessClientDao = businessClientRepository.findByUUID(partnerDto.getBusinessClient().getUuid());
            if (businessClientDao == null)
                throw new ResponseException("No business client found for Id :" + partnerDto.getBusinessClient().getUuid());
            newPartnerDetails.setBusinessClientId(businessClientDao.getId());
        }
        if (partnerDto.getBusinessSegment() != null) {

            BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(partnerDto.getBusinessSegment().getUuid());
            if (businessSegmentDao == null)
                throw new ResponseException("No business segment found for Id :" + partnerDto.getBusinessSegment().getUuid());
            newPartnerDetails.setBusinessSegmentId(businessSegmentDao.getId());
        }

        if (partnerDto.getHomeLocation() != null) {

            LocationDao locationDao = locationRepository.findByUUID(partnerDto.getHomeLocation().getUuid());
            if (locationDao == null)
                throw new ResponseException("No location found for Id :" + partnerDto.getHomeLocation().getUuid());
            newPartnerDetails.setHomeLocationId(locationDao.getId());
            newPartnerDetails.setHomeLocationName(locationDao.getName());
        }


        if (partnerDto.getProfile() != null) {
            newPartnerDetails.setProfileUrl(s3.getUrlForPath(partnerDto.getProfile().getPath(), "profile"));
        }
        newPartnerDetails.setIsVerified(true);
        newPartnerDetails.setOnboardingDate(partnerDto.getOnboardingDate());
        newPartnerDetails.setDeboardingDate(partnerDto.getDeboardingDate());
        newPartnerDetails.setDlExpiryDate(partnerDto.getDlExpiryDate());
        newPartnerDetails.setJoiningDate(partnerDto.getJoiningDate());
        newPartnerDetails.setInterviewDate(partnerDto.getInterviewDate());
        newPartnerDetails.setDlNumber(partnerDto.getDlNumber());
        newPartnerDetails.setSourceOfLeadType(partnerDto.getSourceOfLeadType());
        newPartnerDetails.setSourceOfLead(partnerDto.getSourceOfLead());

        newPartnerDetails.setRemark(partnerDto.getRemark());
        newPartnerDetails.setSelectionStatus(partnerDto.getSelectionStatus());
        newPartnerDetails.setDrivingTestStatus(partnerDto.getDrivingTestStatus());


        return newPartnerDetails;
    }

    private void setPartnerDetails(PartnerDetailDto result, PartnerDao partnerDao, PartnerDetailDao partnerDetails) {
        PartnerViewDto partnerDto = new PartnerViewDto();
        partnerDto.setPunchId(partnerDao.getPunchId());
        partnerDto.setUuid(partnerDao.getUuid());
        partnerDto.setPhoneNumber(partnerDao.getPhoneNumber());
        partnerDto.setProfileUrl(partnerDao.getProfileUrl());

        result.setPartner(partnerDto);


        if (partnerDetails == null)
            return;

        result.getPartner().setProfileUrl(partnerDetails.getProfileUrl());
        result.getPartner().setFirstName(partnerDetails.getFirstName());
        result.getPartner().setLastName(partnerDetails.getLastName());
        result.setState(partnerDetails.getState());
        result.setJoiningDate(partnerDetails.getJoiningDate());
        result.setDlNumber(partnerDetails.getDlNumber());
        result.setDlExpiryDate(partnerDetails.getDlExpiryDate());
        result.setRemark(partnerDetails.getRemark());
        result.setOnboardingDate(partnerDetails.getOnboardingDate());
        result.setDeboardingDate(partnerDetails.getDeboardingDate());
        result.setUuid(partnerDao.getUuid());
        result.setDrivingTestStatus(partnerDetails.getDrivingTestStatus());
        result.setInterviewDate(partnerDetails.getInterviewDate());
        result.setSelectionStatus(partnerDetails.getSelectionStatus());
        result.setIsVerified(partnerDetails.getIsVerified());
        result.setSourceOfLead(partnerDetails.getSourceOfLead());
        result.setSourceOfLeadType(partnerDetails.getSourceOfLeadType());
        result.setFirstName(partnerDetails.getFirstName());
        result.setLastName(partnerDetails.getLastName());
        result.setFathersName(partnerDetails.getFathersName());
        result.setLocalAddress(partnerDetails.getLocalAddress());
        result.setPermanentAddress(partnerDetails.getPermanentAddress());
        result.setAadhaarCardNumber(partnerDetails.getAadhaarCardNumber());
        result.setProfileUrl(partnerDetails.getProfileUrl());
    }

    private void setBusinessClient(PartnerDetailDto result, Integer businessClientId) {
        BusinessClientDao businessClientDao = businessClientRepository.findById(businessClientId);
        if (businessClientDao == null)
            return;
        result.setBusinessClient(BusinessClientDto.builder()
                .name(businessClientDao.getName())
                .description(businessClientDao.getDescription())
                .uuid(businessClientDao.getUuid())
                .build());
    }

    private void setBusinessSegment(PartnerDetailDto result, Integer businessSegmentId) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findById(businessSegmentId);
        if (businessSegmentDao == null)
            return;
        result.setBusinessSegment(BusinessSegmentDto.builder()
                .name(businessSegmentDao.getName())
                .uuid(businessSegmentDao.getUuid())
                .build());
    }

    private void setPartnerHomeLocation(PartnerDetailDto result, Integer homeLocationId) {
        LocationDao locationDao = locationRepository.findById(homeLocationId);
        if (locationDao == null)
            return;
        result.setHomeLocation(LocationDto.builder()
                .coordinates(LatLongDto.builder()
                        .latitude(locationDao.getLatitude().doubleValue())
                        .longitude(locationDao.getLongitude().doubleValue())
                        .build())
                .name(locationDao.getName())
                .type(locationDao.getType())
                .uuid(locationDao.getUuid())
                .build());
    }

}
