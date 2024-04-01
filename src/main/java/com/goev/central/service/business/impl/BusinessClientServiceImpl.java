package com.goev.central.service.business.impl;

import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.central.service.business.BusinessClientService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        PaginatedResponseDto<BusinessClientDto> result = PaginatedResponseDto.<BusinessClientDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<BusinessClientDao> businessClientDaos = businessClientRepository.findAll();
        if (CollectionUtils.isEmpty(businessClientDaos))
            return result;

        for (BusinessClientDao businessClientDao : businessClientDaos) {
            result.getElements().add(BusinessClientDto.builder()
                    .name(businessClientDao.getName())
                    .uuid(businessClientDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public BusinessClientDto createClient(BusinessClientDto businessClientDto) {

        BusinessClientDao businessClientDao = new BusinessClientDao();
        businessClientDao.setName(businessClientDto.getName());
        businessClientDao = businessClientRepository.save(businessClientDao);
        if (businessClientDao == null)
            throw new ResponseException("Error in saving business client");
        return BusinessClientDto.builder().name(businessClientDao.getName()).uuid(businessClientDao.getUuid()).build();
    }

    @Override
    public BusinessClientDto updateClient(String clientUUID, BusinessClientDto businessClientDto) {
        BusinessClientDao businessClientDao = businessClientRepository.findByUUID(clientUUID);
        if (businessClientDao == null)
            throw new ResponseException("No business client found for Id :" + clientUUID);
        BusinessClientDao newBusinessClientDao = new BusinessClientDao();
        newBusinessClientDao.setName(businessClientDto.getName());

        newBusinessClientDao.setId(businessClientDao.getId());
        newBusinessClientDao.setUuid(businessClientDao.getUuid());
        businessClientDao = businessClientRepository.update(newBusinessClientDao);
        if (businessClientDao == null)
            throw new ResponseException("Error in updating details business client");
        return BusinessClientDto.builder()
                .name(businessClientDao.getName())
                .uuid(businessClientDao.getUuid()).build();
    }

    @Override
    public BusinessClientDto getClientDetails(String clientUUID) {
        BusinessClientDao businessClientDao = businessClientRepository.findByUUID(clientUUID);
        if (businessClientDao == null)
            throw new ResponseException("No business client found for Id :" + clientUUID);
        return BusinessClientDto.builder()
                .name(businessClientDao.getName())
                .uuid(businessClientDao.getUuid()).build();
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
