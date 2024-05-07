package com.goev.central.service.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppStringDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppStringDto;
import com.goev.central.repository.partner.app.PartnerAppStringRepository;
import com.goev.central.service.partner.app.PartnerAppStringService;
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
public class PartnerAppStringServiceImpl implements PartnerAppStringService {

    private final PartnerAppStringRepository partnerAppStringRepository;

    @Override
    public PaginatedResponseDto<PartnerAppStringDto> getPartnerAppStrings() {
        PaginatedResponseDto<PartnerAppStringDto> result = PaginatedResponseDto.<PartnerAppStringDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerAppStringDao> partnerAppStringDaos = partnerAppStringRepository.findAll();
        if (CollectionUtils.isEmpty(partnerAppStringDaos))
            return result;

        for (PartnerAppStringDao partnerAppStringDao : partnerAppStringDaos) {
            result.getElements().add(PartnerAppStringDto.builder()
                    .uuid(partnerAppStringDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerAppStringDto createPartnerAppString(PartnerAppStringDto partnerAppStringDto) {

        PartnerAppStringDao partnerAppStringDao = new PartnerAppStringDao();
        partnerAppStringDao = partnerAppStringRepository.save(partnerAppStringDao);
        if (partnerAppStringDao == null)
            throw new ResponseException("Error in saving partnerAppString partnerAppString");
        return PartnerAppStringDto.builder()
                .uuid(partnerAppStringDao.getUuid()).build();
    }

    @Override
    public PartnerAppStringDto updatePartnerAppString(String partnerAppStringUUID, PartnerAppStringDto partnerAppStringDto) {
        PartnerAppStringDao partnerAppStringDao = partnerAppStringRepository.findByUUID(partnerAppStringUUID);
        if (partnerAppStringDao == null)
            throw new ResponseException("No partnerAppString  found for Id :" + partnerAppStringUUID);
        PartnerAppStringDao newPartnerAppStringDao = new PartnerAppStringDao();
       

        newPartnerAppStringDao.setId(partnerAppStringDao.getId());
        newPartnerAppStringDao.setUuid(partnerAppStringDao.getUuid());
        partnerAppStringDao = partnerAppStringRepository.update(newPartnerAppStringDao);
        if (partnerAppStringDao == null)
            throw new ResponseException("Error in updating details partnerAppString ");
        return PartnerAppStringDto.builder()
                .uuid(partnerAppStringDao.getUuid()).build();
    }

    @Override
    public PartnerAppStringDto getPartnerAppStringDetails(String partnerAppStringUUID) {
        PartnerAppStringDao partnerAppStringDao = partnerAppStringRepository.findByUUID(partnerAppStringUUID);
        if (partnerAppStringDao == null)
            throw new ResponseException("No partnerAppString  found for Id :" + partnerAppStringUUID);
        return PartnerAppStringDto.builder()
                .uuid(partnerAppStringDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerAppString(String partnerAppStringUUID) {
        PartnerAppStringDao partnerAppStringDao = partnerAppStringRepository.findByUUID(partnerAppStringUUID);
        if (partnerAppStringDao == null)
            throw new ResponseException("No partnerAppString  found for Id :" + partnerAppStringUUID);
        partnerAppStringRepository.delete(partnerAppStringDao.getId());
        return true;
    }
}
