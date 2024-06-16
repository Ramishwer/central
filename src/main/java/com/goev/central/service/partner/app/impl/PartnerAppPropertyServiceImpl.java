package com.goev.central.service.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppPropertyDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppPropertyDto;
import com.goev.central.repository.partner.app.PartnerAppPropertyRepository;
import com.goev.central.service.partner.app.PartnerAppPropertyService;
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
public class PartnerAppPropertyServiceImpl implements PartnerAppPropertyService {

    private final PartnerAppPropertyRepository partnerAppPropertyRepository;

    @Override
    public PaginatedResponseDto<PartnerAppPropertyDto> getPartnerAppProperties() {
        PaginatedResponseDto<PartnerAppPropertyDto> result = PaginatedResponseDto.<PartnerAppPropertyDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerAppPropertyDao> partnerAppPropertyDaos = partnerAppPropertyRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerAppPropertyDaos))
            return result;

        for (PartnerAppPropertyDao partnerAppPropertyDao : partnerAppPropertyDaos) {
            result.getElements().add(PartnerAppPropertyDto.builder()
                    .uuid(partnerAppPropertyDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerAppPropertyDto createPartnerAppProperty(PartnerAppPropertyDto partnerAppPropertyDto) {

        PartnerAppPropertyDao partnerAppPropertyDao = new PartnerAppPropertyDao();
        partnerAppPropertyDao = partnerAppPropertyRepository.save(partnerAppPropertyDao);
        if (partnerAppPropertyDao == null)
            throw new ResponseException("Error in saving partnerAppProperty partnerAppProperty");
        return PartnerAppPropertyDto.builder()
                .uuid(partnerAppPropertyDao.getUuid()).build();
    }

    @Override
    public PartnerAppPropertyDto updatePartnerAppProperty(String partnerAppPropertyUUID, PartnerAppPropertyDto partnerAppPropertyDto) {
        PartnerAppPropertyDao partnerAppPropertyDao = partnerAppPropertyRepository.findByUUID(partnerAppPropertyUUID);
        if (partnerAppPropertyDao == null)
            throw new ResponseException("No partnerAppProperty  found for Id :" + partnerAppPropertyUUID);
        PartnerAppPropertyDao newPartnerAppPropertyDao = new PartnerAppPropertyDao();


        newPartnerAppPropertyDao.setId(partnerAppPropertyDao.getId());
        newPartnerAppPropertyDao.setUuid(partnerAppPropertyDao.getUuid());
        partnerAppPropertyDao = partnerAppPropertyRepository.update(newPartnerAppPropertyDao);
        if (partnerAppPropertyDao == null)
            throw new ResponseException("Error in updating details partnerAppProperty ");
        return PartnerAppPropertyDto.builder()
                .uuid(partnerAppPropertyDao.getUuid()).build();
    }

    @Override
    public PartnerAppPropertyDto getPartnerAppPropertyDetails(String partnerAppPropertyUUID) {
        PartnerAppPropertyDao partnerAppPropertyDao = partnerAppPropertyRepository.findByUUID(partnerAppPropertyUUID);
        if (partnerAppPropertyDao == null)
            throw new ResponseException("No partnerAppProperty  found for Id :" + partnerAppPropertyUUID);
        return PartnerAppPropertyDto.builder()
                .uuid(partnerAppPropertyDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerAppProperty(String partnerAppPropertyUUID) {
        PartnerAppPropertyDao partnerAppPropertyDao = partnerAppPropertyRepository.findByUUID(partnerAppPropertyUUID);
        if (partnerAppPropertyDao == null)
            throw new ResponseException("No partnerAppProperty  found for Id :" + partnerAppPropertyUUID);
        partnerAppPropertyRepository.delete(partnerAppPropertyDao.getId());
        return true;
    }
}
