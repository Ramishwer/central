package com.goev.central.service.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppEventDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppEventDto;
import com.goev.central.repository.partner.app.PartnerAppEventRepository;
import com.goev.central.service.partner.app.PartnerAppEventService;
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
public class PartnerAppEventServiceImpl implements PartnerAppEventService {

    private final PartnerAppEventRepository partnerAppEventRepository;

    @Override
    public PaginatedResponseDto<PartnerAppEventDto> getPartnerAppEvents(String partnerUUID) {
        PaginatedResponseDto<PartnerAppEventDto> result = PaginatedResponseDto.<PartnerAppEventDto>builder().elements(new ArrayList<>()).build();
        List<PartnerAppEventDao> partnerAppEventDaos = partnerAppEventRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerAppEventDaos))
            return result;

        for (PartnerAppEventDao partnerAppEventDao : partnerAppEventDaos) {
            result.getElements().add(PartnerAppEventDto.builder()
                    .uuid(partnerAppEventDao.getUuid())
                    .build());
        }
        return result;
    }


    @Override
    public PartnerAppEventDto getPartnerAppEventDetails(String partnerUUID, String partnerAppEventUUID) {
        PartnerAppEventDao partnerAppEventDao = partnerAppEventRepository.findByUUID(partnerAppEventUUID);
        if (partnerAppEventDao == null)
            throw new ResponseException("No partnerAppEvent  found for Id :" + partnerAppEventUUID);
        return PartnerAppEventDto.builder()
                .uuid(partnerAppEventDao.getUuid()).build();
    }

}
