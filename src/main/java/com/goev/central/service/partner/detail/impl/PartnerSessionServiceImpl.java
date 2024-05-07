package com.goev.central.service.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerSessionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSessionDto;
import com.goev.central.repository.partner.detail.PartnerSessionRepository;
import com.goev.central.service.partner.detail.PartnerSessionService;
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
public class PartnerSessionServiceImpl implements PartnerSessionService {

    private final PartnerSessionRepository partnerSessionRepository;

    @Override
    public PaginatedResponseDto<PartnerSessionDto> getPartnerSessions(String partnerUUID) {
        PaginatedResponseDto<PartnerSessionDto> result = PaginatedResponseDto.<PartnerSessionDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerSessionDao> partnerSessionDaos = partnerSessionRepository.findAll();
        if (CollectionUtils.isEmpty(partnerSessionDaos))
            return result;

        for (PartnerSessionDao partnerSessionDao : partnerSessionDaos) {
            result.getElements().add(PartnerSessionDto.builder()
                    .uuid(partnerSessionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerSessionDto getPartnerSessionDetails(String partnerUUID, String partnerSessionUUID) {
        PartnerSessionDao partnerSessionDao = partnerSessionRepository.findByUUID(partnerSessionUUID);
        if (partnerSessionDao == null)
            throw new ResponseException("No partnerSession  found for Id :" + partnerSessionUUID);
        return PartnerSessionDto.builder()
                .uuid(partnerSessionDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerSession(String partnerUUID, String partnerSessionUUID) {
        PartnerSessionDao partnerSessionDao = partnerSessionRepository.findByUUID(partnerSessionUUID);
        if (partnerSessionDao == null)
            throw new ResponseException("No partnerSession  found for Id :" + partnerSessionUUID);
        partnerSessionRepository.delete(partnerSessionDao.getId());
        return true;
    }
}
