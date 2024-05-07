package com.goev.central.service.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerAttributeDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerAttributeDto;
import com.goev.central.repository.partner.detail.PartnerAttributeRepository;
import com.goev.central.service.partner.detail.PartnerAttributeService;
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
public class PartnerAttributeServiceImpl implements PartnerAttributeService {

    private final PartnerAttributeRepository partnerAttributeRepository;

    @Override
    public PaginatedResponseDto<PartnerAttributeDto> getPartnerAttributes(String partnerUUID) {
        PaginatedResponseDto<PartnerAttributeDto> result = PaginatedResponseDto.<PartnerAttributeDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerAttributeDao> partnerAttributeDaos = partnerAttributeRepository.findAll();
        if (CollectionUtils.isEmpty(partnerAttributeDaos))
            return result;

        for (PartnerAttributeDao partnerAttributeDao : partnerAttributeDaos) {
            result.getElements().add(PartnerAttributeDto.builder()
                    .uuid(partnerAttributeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerAttributeDto createPartnerAttribute(String partnerUUID, PartnerAttributeDto partnerAttributeDto) {

        PartnerAttributeDao partnerAttributeDao = new PartnerAttributeDao();
        partnerAttributeDao = partnerAttributeRepository.save(partnerAttributeDao);
        if (partnerAttributeDao == null)
            throw new ResponseException("Error in saving partnerAttribute partnerAttribute");
        return PartnerAttributeDto.builder()
                .uuid(partnerAttributeDao.getUuid()).build();
    }

    @Override
    public PartnerAttributeDto updatePartnerAttribute(String partnerUUID, String partnerAttributeUUID, PartnerAttributeDto partnerAttributeDto) {
        PartnerAttributeDao partnerAttributeDao = partnerAttributeRepository.findByUUID(partnerAttributeUUID);
        if (partnerAttributeDao == null)
            throw new ResponseException("No partnerAttribute  found for Id :" + partnerAttributeUUID);
        PartnerAttributeDao newPartnerAttributeDao = new PartnerAttributeDao();
       

        newPartnerAttributeDao.setId(partnerAttributeDao.getId());
        newPartnerAttributeDao.setUuid(partnerAttributeDao.getUuid());
        partnerAttributeDao = partnerAttributeRepository.update(newPartnerAttributeDao);
        if (partnerAttributeDao == null)
            throw new ResponseException("Error in updating details partnerAttribute ");
        return PartnerAttributeDto.builder()
                .uuid(partnerAttributeDao.getUuid()).build();
    }

    @Override
    public PartnerAttributeDto getPartnerAttributeDetails(String partnerUUID, String partnerAttributeUUID) {
        PartnerAttributeDao partnerAttributeDao = partnerAttributeRepository.findByUUID(partnerAttributeUUID);
        if (partnerAttributeDao == null)
            throw new ResponseException("No partnerAttribute  found for Id :" + partnerAttributeUUID);
        return PartnerAttributeDto.builder()
                .uuid(partnerAttributeDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerAttribute(String partnerUUID, String partnerAttributeUUID) {
        PartnerAttributeDao partnerAttributeDao = partnerAttributeRepository.findByUUID(partnerAttributeUUID);
        if (partnerAttributeDao == null)
            throw new ResponseException("No partnerAttribute  found for Id :" + partnerAttributeUUID);
        partnerAttributeRepository.delete(partnerAttributeDao.getId());
        return true;
    }
}
