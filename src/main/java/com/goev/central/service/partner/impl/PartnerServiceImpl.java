package com.goev.central.service.partner.impl;


import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dao.business.BusinessSegmentDao;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.partner.detail.PartnerAccountDetailDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.dao.partner.detail.PartnerReferenceDao;
import com.goev.central.dao.partner.document.PartnerDocumentDao;
import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerAccountDto;
import com.goev.central.dto.partner.detail.PartnerDetailsDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.partner.detail.PartnerReferenceDto;
import com.goev.central.dto.partner.document.PartnerDocumentDto;
import com.goev.central.enums.DocumentStatus;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.repository.partner.detail.PartnerAccountDetailRepository;
import com.goev.central.repository.partner.detail.PartnerDetailRepository;
import com.goev.central.repository.partner.detail.PartnerReferenceRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.document.PartnerDocumentRepository;
import com.goev.central.repository.partner.document.PartnerDocumentTypeRepository;
import com.goev.central.service.partner.PartnerService;
import com.goev.central.utilities.S3Utils;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerServiceImpl implements PartnerService {


    private final PartnerRepository partnerRepository;
    private final PartnerDocumentTypeRepository partnerDocumentTypeRepository;
    private final PartnerDocumentRepository partnerDocumentRepository;
    private final PartnerDetailRepository partnerDetailRepository;
    private final PartnerReferenceRepository partnerReferenceRepository;
    private final PartnerAccountDetailRepository partnerAccountDetailRepository;
    private final LocationRepository locationRepository;
    private final BusinessClientRepository businessClientRepository;
    private final BusinessSegmentRepository businessSegmentRepository;
    private final S3Utils s3;

    @Override
    public PartnerDetailsDto createPartner(PartnerDetailsDto partnerDto) {
        PartnerDao existingPartner = partnerRepository.findByPhoneNumber(partnerDto.getDetails().getPhoneNumber());

        if (existingPartner != null) {
            throw new ResponseException("Error in saving partner: Partner with Phone Number :" + partnerDto.getDetails().getPhoneNumber() + " already exist");
        }
        PartnerDao partnerDao = new PartnerDao();

        partnerDao.setPunchId(partnerDto.getDetails().getPunchId());
        partnerDao.setPhoneNumber(partnerDto.getDetails().getPhoneNumber());
        PartnerDao partner = partnerRepository.save(partnerDao);

        if (partner == null)
            throw new ResponseException("Error in saving details");

        PartnerDetailDao partnerDetails = getPartnerDetailDao(partnerDto);
        partnerDetails.setPartnerId(partner.getId());
        partnerDetails = partnerDetailRepository.save(partnerDetails);
        if (partnerDetails == null)
            throw new ResponseException("Error in saving partner details");

        partner.setPartnerDetailsId(partnerDetails.getId());
        partnerRepository.update(partner);
        savePartnerDocuments(partnerDto.getDocuments(), partner.getId());

        PartnerDetailsDto result = PartnerDetailsDto.builder().build();
        setPartnerDetails(result, partner, partnerDetails);
        setPartnerHomeLocation(result, partnerDetails.getHomeLocationId());
        setBusinessSegment(result, partnerDetails.getBusinessSegmentId());
        setBusinessClient(result, partnerDetails.getBusinessClientId());

        setPartnerAccounts(result, partner.getId());
        setPartnerReferences(result, partner.getId());
        setPartnerDocuments(result, partner.getId());

        return result;
    }

    private void setPartnerDetails(PartnerDetailsDto result, PartnerDao partnerDao, PartnerDetailDao partnerDetails) {
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setPunchId(partnerDao.getPunchId());
        partnerDto.setState(partnerDao.getState());
        partnerDto.setUuid(partnerDao.getUuid());
        partnerDto.setPhoneNumber(partnerDao.getPhoneNumber());
        partnerDto.setAuthUUID(partnerDao.getAuthUuid());

        result.setDetails(partnerDto);


        if(partnerDetails == null)
            return;

        result.setJoiningDate(partnerDetails.getJoiningDate());
        result.setDlNumber(partnerDetails.getDlNumber());
        result.setDlExpiry(partnerDetails.getDlExpiry());
        result.setRemark(partnerDetails.getRemark());
        result.setOnboardingDate(partnerDetails.getOnboardingDate());
        result.setDeboardingDate(partnerDetails.getDeboardingDate());
        result.setUuid(partnerDao.getUuid());
        result.setDriverTestStatus(partnerDetails.getDriverTestStatus());
        result.setInterviewDate(partnerDetails.getInterviewDate());
        result.setSelectionStatus(partnerDetails.getSelectionStatus());
        result.setIsVerified(partnerDetails.getIsVerified());
        result.setSourceOfLead(partnerDetails.getSourceOfLead());
        result.setSourceOfLeadType(partnerDetails.getSourceOfLeadType());
        result.getDetails().setFirstName(partnerDetails.getFirstName());
        result.getDetails().setLastName(partnerDetails.getLastName());
        result.getDetails().setFathersName(partnerDetails.getFathersName());
        result.getDetails().setLocalAddress(partnerDetails.getLocalAddress());
        result.getDetails().setPermanentAddress(partnerDetails.getPermanentAddress());
    }

    private void setBusinessClient(PartnerDetailsDto result, Integer businessClientId) {
        BusinessClientDao businessClientDao = businessClientRepository.findById(businessClientId);
        if (businessClientDao == null)
            return;
        result.setBusinessClient(BusinessClientDto.builder()
                .name(businessClientDao.getName())
                .uuid(businessClientDao.getUuid())
                .build());
    }

    private void setBusinessSegment(PartnerDetailsDto result, Integer businessSegmentId) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findById(businessSegmentId);
        if (businessSegmentDao == null)
            return;
        result.setBusinessSegment(BusinessSegmentDto.builder()
                .name(businessSegmentDao.getName())
                .uuid(businessSegmentDao.getUuid())
                .build());
    }

    private void setPartnerHomeLocation(PartnerDetailsDto result, Integer homeLocationId) {
        LocationDao locationDao = locationRepository.findById(homeLocationId);
        if (locationDao == null)
            return;
        result.setHomeLocation(LocationDto.builder()
                .latitude(locationDao.getLatitude())
                .longitude(locationDao.getLongitude())
                .name(locationDao.getName())
                .type(locationDao.getType())
                .uuid(locationDao.getUuid())
                .build());
    }

    @Override
    public PartnerDetailsDto updatePartner(String partnerUUID, PartnerDetailsDto partnerDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDetailDao oldPartnerDetailDao = partnerDetailRepository.findById(partner.getPartnerDetailsId());
        if (oldPartnerDetailDao == null)
            throw new ResponseException("No partner details found for Id :" + partnerUUID);

        PartnerDetailDao newPartnerDetailDao = getPartnerDetailDao(partnerDto);
        newPartnerDetailDao.setPartnerId(oldPartnerDetailDao.getPartnerId());
        newPartnerDetailDao = partnerDetailRepository.save(newPartnerDetailDao);

        partnerDetailRepository.delete(oldPartnerDetailDao.getId());

        partner.setPartnerDetailsId(newPartnerDetailDao.getId());
        partner = partnerRepository.update(partner);
        if (partner == null)
            throw new ResponseException("Error in saving details");


        PartnerDetailsDto result = PartnerDetailsDto.builder().build();
        setPartnerDetails(result, partner, newPartnerDetailDao);
        setPartnerHomeLocation(result, newPartnerDetailDao.getHomeLocationId());
        setBusinessSegment(result, newPartnerDetailDao.getBusinessSegmentId());
        setBusinessClient(result, newPartnerDetailDao.getBusinessClientId());

        setPartnerAccounts(result, partner.getId());
        setPartnerReferences(result, partner.getId());
        setPartnerDocuments(result, partner.getId());

        return result;
    }

    private PartnerDetailDao getPartnerDetailDao(PartnerDetailsDto partnerDto) {
        PartnerDetailDao newPartnerDetails = new PartnerDetailDao();

        if (partnerDto.getDetails() != null) {
            newPartnerDetails.setFirstName(partnerDto.getDetails().getFirstName());
            newPartnerDetails.setLastName(partnerDto.getDetails().getLastName());
            newPartnerDetails.setEmail(partnerDto.getDetails().getEmail());
            newPartnerDetails.setAadhaarCardNumber(partnerDto.getDetails().getAadhaarCardNumber());
            newPartnerDetails.setProfileUrl(partnerDto.getDetails().getProfileUrl());
            newPartnerDetails.setLocalAddress(partnerDto.getDetails().getLocalAddress());
            newPartnerDetails.setPermanentAddress(partnerDto.getDetails().getPermanentAddress());
            newPartnerDetails.setFathersName(partnerDto.getDetails().getFathersName());
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
        newPartnerDetails.setDlExpiry(partnerDto.getDlExpiry());
        newPartnerDetails.setJoiningDate(partnerDto.getJoiningDate());
        newPartnerDetails.setInterviewDate(partnerDto.getInterviewDate());
        newPartnerDetails.setDlNumber(partnerDto.getDlNumber());
        newPartnerDetails.setSourceOfLeadType(partnerDto.getSourceOfLeadType());
        newPartnerDetails.setSourceOfLead(partnerDto.getSourceOfLead());

        newPartnerDetails.setRemark(partnerDto.getRemark());
        newPartnerDetails.setSelectionStatus(partnerDto.getSelectionStatus());
        newPartnerDetails.setDriverTestStatus(partnerDto.getDriverTestStatus());



        return newPartnerDetails;
    }

    @Override
    public PartnerDetailsDto getPartnerDetails(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDetailDao partnerDetailDao = partnerDetailRepository.findById(partner.getPartnerDetailsId());

        if (partnerDetailDao == null)
            throw new ResponseException("No partner details found for Id :" + partnerUUID);


        PartnerDetailsDto result = PartnerDetailsDto.builder().build();
        setPartnerDetails(result, partner, partnerDetailDao);
        setPartnerHomeLocation(result, partnerDetailDao.getHomeLocationId());
        setBusinessSegment(result, partnerDetailDao.getBusinessSegmentId());
        setBusinessClient(result, partnerDetailDao.getBusinessClientId());

        setPartnerAccounts(result, partner.getId());
        setPartnerReferences(result, partner.getId());
        setPartnerDocuments(result, partner.getId());

        return result;
    }

    @Override
    public Boolean deletePartner(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);
        partnerRepository.delete(partner.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<PartnerViewDto> getPartners() {

        PaginatedResponseDto<PartnerViewDto> result = PaginatedResponseDto.<PartnerViewDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<PartnerDao> partners = partnerRepository.findAll();
        if (CollectionUtils.isEmpty(partners))
            return result;

        List<PartnerDetailDao> partnerDetailsForPartners = partnerDetailRepository.findAllByIds(partners.stream().map(PartnerDao::getPartnerDetailsId).collect(Collectors.toList()));
        Map<Integer, PartnerDetailDao> partnerDetailMap = partnerDetailsForPartners.stream().collect(Collectors.toMap(PartnerDetailDao::getId, Function.identity()));
        for (PartnerDao partner : partners) {
            PartnerDetailDao partnerDetails = partnerDetailMap.get(partner.getPartnerDetailsId());
            result.getElements().add(PartnerViewDto.builder()
                    .profileUrl(partnerDetails.getProfileUrl())
                    .punchId(partner.getPunchId())
                    .phoneNumber(partner.getPhoneNumber())
                    .firstName(partnerDetails.getFirstName())
                    .lastName(partnerDetails.getLastName())
                    .uuid(partner.getUuid())
                    .state(partner.getState())
                    .onboardingDate(partnerDetails.getOnboardingDate())
                    .build());
        }
        return result;
    }

    private void setPartnerAccounts(PartnerDetailsDto result, Integer partnerId) {

        List<PartnerAccountDetailDao> accounts = partnerAccountDetailRepository.findAllByPartnerId(partnerId);
        if (CollectionUtils.isEmpty(accounts))
            return;
        List<PartnerAccountDto> accountsList = new ArrayList<>();
        accounts.forEach(x -> accountsList.add(PartnerAccountDto.builder()
                .accountHolderName(x.getAccountHolderName())
                .ifscCode(x.getIfscCode())
                .uuid(x.getUuid())
                .accountNumber(x.getAccountNumber())
                .build()));
        result.setAccounts(accountsList);
    }

    private void setPartnerReferences(PartnerDetailsDto result, Integer partnerId) {

        List<PartnerReferenceDao> references = partnerReferenceRepository.findAllByPartnerId(partnerId);
        if (CollectionUtils.isEmpty(references))
            return;
        List<PartnerReferenceDto> referencesList = new ArrayList<>();
        references.forEach(x -> referencesList.add(PartnerReferenceDto.builder()
                .email(x.getEmail())
                .address(x.getAddress())
                .phoneNumber(x.getPhoneNumber())
                .relation(x.getRelation())
                .uuid(x.getUuid())
                .name(x.getName())
                .build()));
        result.setReferences(referencesList);
    }

    private void setPartnerDocuments(PartnerDetailsDto result, Integer partnerId) {
        List<PartnerDocumentTypeDao> activeDocumentTypes = partnerDocumentTypeRepository.findAll();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return;
        Map<Integer, PartnerDocumentTypeDao> documentTypeIdToDocumentTypeMap = activeDocumentTypes.stream()
                .collect(Collectors.toMap(PartnerDocumentTypeDao::getId, Function.identity()));
        List<Integer> activeDocumentTypeIds = activeDocumentTypes.stream().map(PartnerDocumentTypeDao::getId).toList();


        Map<Integer, PartnerDocumentDao> existingDocumentMap = partnerDocumentRepository.getExistingDocumentMap(partnerId, activeDocumentTypeIds);
        List<PartnerDocumentDto> documentList = PartnerDetailsDto.getPartnerDocumentDtoList(documentTypeIdToDocumentTypeMap, existingDocumentMap);
        result.setDocuments(documentList);
    }


    private void savePartnerDocuments(List<PartnerDocumentDto> documents, Integer partnerId) {
        List<PartnerDocumentTypeDao> activeDocumentTypes = partnerDocumentTypeRepository.findAll();
        if (CollectionUtils.isEmpty(activeDocumentTypes))
            return;
        Map<String, PartnerDocumentTypeDao> uuidToPartnerDocumentTypeMap = activeDocumentTypes.stream().collect(Collectors.toMap(PartnerDocumentTypeDao::getUuid, Function.identity()));
        Map<Integer, PartnerDocumentTypeDao> idToPartnerDocumentTypeMap = activeDocumentTypes.stream().collect(Collectors.toMap(PartnerDocumentTypeDao::getId, Function.identity()));
        Map<Integer, PartnerDocumentDao> existingDocumentMap = partnerDocumentRepository.getExistingDocumentMap(partnerId, new ArrayList<>(idToPartnerDocumentTypeMap.keySet()));
        Map<Integer, PartnerDocumentDao> newDocumentMap = getDocumentMap(documents, partnerId, uuidToPartnerDocumentTypeMap);
        List<PartnerDocumentDao> documentToDelete = new ArrayList<>();

        for (Map.Entry<Integer, PartnerDocumentTypeDao> entry : idToPartnerDocumentTypeMap.entrySet()) {
            Integer typeId = entry.getKey();
            PartnerDocumentDao existingDoc = existingDocumentMap.getOrDefault(typeId, null);
            PartnerDocumentDao newDoc = newDocumentMap.getOrDefault(typeId, null);

            if (existingDoc == null && newDoc == null)
                continue;

            if (newDoc != null) {
                if (existingDoc != null) {
                    if (existingDoc.getUuid().equals(newDoc.getUuid()))
                        continue;
                    documentToDelete.add(existingDoc);
                }

                newDoc.setUrl(s3.getUrlForPath(newDoc.getUrl(),idToPartnerDocumentTypeMap.get(typeId).getS3Key()));
                partnerDocumentRepository.save(newDoc);
            }
            if (existingDoc != null && newDoc == null) {
                documentToDelete.add(existingDoc);
            }
        }
        if (!CollectionUtils.isEmpty(documentToDelete)) {
            for (PartnerDocumentDao document : documentToDelete) {
                partnerDocumentRepository.delete(document.getId());
            }
        }
    }

    private Map<Integer, PartnerDocumentDao> getDocumentMap(List<PartnerDocumentDto> documents, Integer partnerId, Map<String, PartnerDocumentTypeDao> uuidToPartnerDocumentTypeMap) {
        Map<Integer, PartnerDocumentDao> result = new HashMap<>();
        for (PartnerDocumentDto documentDto : documents) {
            if (documentDto.getType() != null && documentDto.getType().getUuid() != null) {
                PartnerDocumentTypeDao type = uuidToPartnerDocumentTypeMap.get(documentDto.getType().getUuid());
                if (type != null) {
                    PartnerDocumentDao partnerDocumentDao = getPartnerDocumentDao(partnerId, documentDto, type);
                    result.put(type.getId(), partnerDocumentDao);
                }
            }
        }
        return result;
    }


    private PartnerDocumentDao getPartnerDocumentDao(Integer id, PartnerDocumentDto document, PartnerDocumentTypeDao partnerDocumentTypeDao) {
        PartnerDocumentDao partnerDocumentDao = new PartnerDocumentDao();
        partnerDocumentDao.setPartnerId(id);
        partnerDocumentDao.setStatus(DocumentStatus.UPLOADED.name());
        partnerDocumentDao.setFileName(document.getFileName());
        partnerDocumentDao.setPartnerDocumentTypeId(partnerDocumentTypeDao.getId());
//            partnerDocumentDao.setExtension(document.get);
        partnerDocumentDao.setUrl(document.getUrl());
        partnerDocumentDao.setDescription(document.getDescription());
        return partnerDocumentDao;
    }
}
