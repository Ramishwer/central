package com.goev.central.service.business.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dao.business.BusinessClientDetailDao;
import com.goev.central.dao.business.BusinessSegmentDao;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.business.BusinessClientDetailRepository;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.service.business.BusinessClientService;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.dto.ContactDetailsDto;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BusinessClientServiceImpl implements BusinessClientService {

    private final BusinessClientRepository businessClientRepository;
    private final BusinessClientDetailRepository businessClientDetailRepository;
    private final BusinessSegmentRepository businessSegmentRepository;

    @Override
    public PaginatedResponseDto<BusinessClientDto> getClients() {
        PaginatedResponseDto<BusinessClientDto> result = PaginatedResponseDto.<BusinessClientDto>builder().elements(new ArrayList<>()).build();
        List<BusinessClientDao> businessClientDaos = businessClientRepository.findAllActive();
        if (CollectionUtils.isEmpty(businessClientDaos))
            return result;

        for (BusinessClientDao businessClientDao : businessClientDaos) {
            result.getElements().add(getBusinessClientDto(businessClientDao));
        }
        return result;
    }

    private BusinessClientDto getBusinessClientDto(BusinessClientDao businessClientDao) {

        BusinessClientDetailDao details = businessClientDetailRepository.findById(businessClientDao.getBusinessClientDetailsId());
        BusinessClientDto result = BusinessClientDto.builder()
                .name(businessClientDao.getName())
                .description(businessClientDao.getDescription())
                .uuid(businessClientDao.getUuid())
                .build();
        if (details != null) {
            result.setBusinessAddress(ApplicationConstants.GSON.fromJson(details.getBusinessAddressDetails(), LatLongDto.class));
            result.setAppType(details.getAppType());
            result.setContactPersonDetails(ApplicationConstants.GSON.fromJson(details.getContactPersonDetails(), ContactDetailsDto.class));
            result.setGstNumber(details.getGstNumber());

            if (details.getBusinessSegmentId() != null) {
                BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findById(details.getBusinessSegmentId());
                if (businessSegmentDao != null)
                    result.setBusinessSegment(BusinessSegmentDto.fromDao(businessSegmentDao));
            }
        }

        return result;
    }

    @Override
    public BusinessClientDto createClient(BusinessClientDto businessClientDto) {

        BusinessClientDao businessClientDao = getBusinessClientDao(businessClientDto);
        businessClientDao.setDisplayCode("CT-" + SecretGenerationUtils.getCode());
        businessClientDao = businessClientRepository.save(businessClientDao);
        if (businessClientDao == null)
            throw new ResponseException("Error in saving business client");
        BusinessClientDetailDao detailDao = getBusinessClientDetailDao(businessClientDto);
        detailDao.setBusinessClientId(businessClientDao.getId());
        detailDao = businessClientDetailRepository.save(detailDao);
        businessClientDao.setBusinessClientDetailsId(detailDao.getId());
        businessClientDao = businessClientRepository.update(businessClientDao);

        return getBusinessClientDto(businessClientDao);
    }

    private BusinessClientDetailDao getBusinessClientDetailDao(BusinessClientDto businessClientDto) {
        BusinessClientDetailDao businessClientDetailDao = new BusinessClientDetailDao();
        businessClientDetailDao.setGstNumber(businessClientDto.getGstNumber());
        businessClientDetailDao.setAppType(businessClientDto.getAppType());
        if (businessClientDto.getBusinessAddress() != null)
            businessClientDetailDao.setBusinessAddressDetails(ApplicationConstants.GSON.toJson(businessClientDto.getBusinessAddress()));

        if (businessClientDto.getContactPersonDetails() != null)
            businessClientDetailDao.setContactPersonDetails(ApplicationConstants.GSON.toJson(businessClientDto.getContactPersonDetails()));
        if (businessClientDto.getBusinessSegment() != null) {
            BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(businessClientDto.getBusinessSegment().getUuid());
            if (businessSegmentDao == null)
                throw new ResponseException("No business segment found for Id :" + businessClientDto.getBusinessSegment().getUuid());
            businessClientDetailDao.setBusinessSegmentId(businessSegmentDao.getId());
        }

        return businessClientDetailDao;
    }

    private BusinessClientDao getBusinessClientDao(BusinessClientDto businessClientDto) {
        BusinessClientDao businessClientDao = new BusinessClientDao();
        businessClientDao.setName(businessClientDto.getName());
        businessClientDao.setDescription(businessClientDto.getDescription());
        return businessClientDao;
    }

    @Override
    public BusinessClientDto updateClient(String clientUUID, BusinessClientDto businessClientDto) {
        BusinessClientDao businessClientDao = businessClientRepository.findByUUID(clientUUID);
        if (businessClientDao == null)
            throw new ResponseException("No business client found for Id :" + clientUUID);

        BusinessClientDetailDao exisitingDetailDao = businessClientDetailRepository.findById(businessClientDao.getBusinessClientDetailsId());

        if (exisitingDetailDao == null)
            throw new ResponseException("No business details found for Id :" + clientUUID);


        BusinessClientDetailDao newDetailDao = getBusinessClientDetailDao(businessClientDto);
        newDetailDao.setBusinessClientId(businessClientDao.getId());
        newDetailDao = businessClientDetailRepository.save(newDetailDao);
        if (newDetailDao == null)
            throw new ResponseException("Error in saving business details");
        businessClientDetailRepository.delete(exisitingDetailDao.getId());
        BusinessClientDao newBusinessClientDao = getBusinessClientDao(businessClientDto);
        newBusinessClientDao.setId(businessClientDao.getId());
        newBusinessClientDao.setUuid(businessClientDao.getUuid());
        newBusinessClientDao.setBusinessClientDetailsId(newDetailDao.getId());
        businessClientDao = businessClientRepository.update(newBusinessClientDao);
        if (businessClientDao == null)
            throw new ResponseException("Error in updating details business client");
        return getBusinessClientDto(businessClientDao);
    }

    @Override
    public BusinessClientDto getClientDetails(String clientUUID) {
        BusinessClientDao businessClientDao = businessClientRepository.findByUUID(clientUUID);
        if (businessClientDao == null)
            throw new ResponseException("No business client found for Id :" + clientUUID);
        return getBusinessClientDto(businessClientDao);
    }

    @Override
    public Boolean deleteClient(String clientUUID) {
        BusinessClientDao businessClientDao = businessClientRepository.findByUUID(clientUUID);
        if (businessClientDao == null)
            throw new ResponseException("No business client found for Id :" + clientUUID);
        businessClientRepository.delete(businessClientDao.getId());
        return true;
    }
}
