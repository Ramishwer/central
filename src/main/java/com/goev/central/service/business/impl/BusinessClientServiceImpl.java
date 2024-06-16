package com.goev.central.service.business.impl;

import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.service.business.BusinessClientService;
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

    @Override
    public PaginatedResponseDto<BusinessClientDto> getClients() {
        PaginatedResponseDto<BusinessClientDto> result = PaginatedResponseDto.<BusinessClientDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<BusinessClientDao> businessClientDaos = businessClientRepository.findAllActive();
        if (CollectionUtils.isEmpty(businessClientDaos))
            return result;

        for (BusinessClientDao businessClientDao : businessClientDaos) {
            result.getElements().add(getBusinessClientDto(businessClientDao));
        }
        return result;
    }

    private BusinessClientDto getBusinessClientDto(BusinessClientDao businessClientDao) {
        return BusinessClientDto.builder()
                .name(businessClientDao.getName())
                .description(businessClientDao.getDescription())
                .uuid(businessClientDao.getUuid())
                .build();
    }

    @Override
    public BusinessClientDto createClient(BusinessClientDto businessClientDto) {

        BusinessClientDao businessClientDao = getBusinessClientDao(businessClientDto);
        businessClientDao = businessClientRepository.save(businessClientDao);
        if (businessClientDao == null)
            throw new ResponseException("Error in saving business client");
        return getBusinessClientDto(businessClientDao);
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
        BusinessClientDao newBusinessClientDao = getBusinessClientDao(businessClientDto);
        newBusinessClientDao.setId(businessClientDao.getId());
        newBusinessClientDao.setUuid(businessClientDao.getUuid());
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
