package com.goev.central.service.partner.detail.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dao.business.BusinessSegmentDao;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDetailDto;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.repository.partner.detail.PartnerDetailRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.detail.PartnerDetailService;
import com.goev.central.utilities.S3Utils;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.exceptions.ResponseException;
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
    private final S3Utils s3;

    @Override
    public PartnerDetailDto createPartner(PartnerDetailDto partnerDto) {
        PartnerDao existingPartner = partnerRepository.findByPhoneNumber(partnerDto.getPartner().getPhoneNumber());

        if (existingPartner != null) {
            throw new ResponseException("Error in saving partner: Partner with Phone Number :" + partnerDto.getPartner().getPhoneNumber() + " already exist");
        }
        PartnerDao partnerDao = new PartnerDao();

        partnerDao.setPunchId(partnerDto.getPartner().getPunchId());
        partnerDao.setPhoneNumber(partnerDto.getPartner().getPhoneNumber());
        PartnerDao partner = partnerRepository.save(partnerDao);

        if (partner == null)
            throw new ResponseException("Error in saving details");

        PartnerDetailDao partnerDetailDao = getPartnerDetailDao(partnerDto);
        partnerDetailDao.setPartnerId(partner.getId());
        partnerDetailDao = partnerDetailRepository.save(partnerDetailDao);
        if (partnerDetailDao == null)
            throw new ResponseException("Error in saving partner details");

        partner.setPartnerDetailsId(partnerDetailDao.getId());
        partner.setViewInfo(ApplicationConstants.GSON.toJson(getPartnerViewDto(partnerDetailDao, partner)));
        partnerRepository.update(partner);

        return getPartnerDetailDto(partnerDetailDao,partner);


    }

    private PartnerViewDto getPartnerViewDto(PartnerDetailDao partnerDetails, PartnerDao partnerDao) {
        LocationDao locationDao = locationRepository.findById(partnerDetails.getHomeLocationId());
        return PartnerViewDto.builder()
                .firstName(partnerDetails.getFirstName())
                .lastName(partnerDetails.getLastName())
                .punchId(partnerDao.getPunchId())
                .homeLocation(LocationDto.builder().uuid(locationDao.getUuid()).name(locationDao.getName()).build())
                .profileUrl(partnerDao.getProfileUrl())
                .phoneNumber(partnerDao.getPhoneNumber())
                .onboardingDate(partnerDetails.getOnboardingDate())
                .build();
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


        return getPartnerDetailDto(partnerDetailDao,partnerDao);
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

        partner.setPartnerDetailsId(partnerDetails.getId());
        partner.setViewInfo(ApplicationConstants.GSON.toJson(getPartnerViewDto(partnerDetails, partner)));
        partnerRepository.update(partner);

        return getPartnerDetailDto(partnerDetailDao,partner);
    }


    private PartnerDetailDao getPartnerDetailDao(PartnerDetailDto partnerDto) {
        PartnerDetailDao newPartnerDetails = new PartnerDetailDao();

        if (partnerDto.getPartner() != null) {
            newPartnerDetails.setFirstName(partnerDto.getFirstName());
            newPartnerDetails.setLastName(partnerDto.getLastName());
            newPartnerDetails.setEmail(partnerDto.getEmail());
            newPartnerDetails.setAadhaarCardNumber(partnerDto.getAadhaarCardNumber());
            newPartnerDetails.setProfileUrl(partnerDto.getProfileUrl());
            newPartnerDetails.setLocalAddress(partnerDto.getLocalAddress());
            newPartnerDetails.setPermanentAddress(partnerDto.getPermanentAddress());
            newPartnerDetails.setFathersName(partnerDto.getFathersName());
        }

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
