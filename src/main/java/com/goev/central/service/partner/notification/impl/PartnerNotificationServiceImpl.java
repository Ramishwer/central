package com.goev.central.service.partner.notification.impl;

import com.goev.central.dao.partner.notification.PartnerNotificationDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.notification.PartnerNotificationDto;
import com.goev.central.repository.partner.notification.PartnerNotificationRepository;
import com.goev.central.service.partner.notification.PartnerNotificationService;
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
public class PartnerNotificationServiceImpl implements PartnerNotificationService {

    private final PartnerNotificationRepository partnerNotificationRepository;

    @Override
    public PaginatedResponseDto<PartnerNotificationDto> getPartnerNotifications(String partnerUUID) {
        PaginatedResponseDto<PartnerNotificationDto> result = PaginatedResponseDto.<PartnerNotificationDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerNotificationDao> partnerNotificationDaos = partnerNotificationRepository.findAll();
        if (CollectionUtils.isEmpty(partnerNotificationDaos))
            return result;

        for (PartnerNotificationDao partnerNotificationDao : partnerNotificationDaos) {
            result.getElements().add(PartnerNotificationDto.builder()
                    .uuid(partnerNotificationDao.getUuid())
                    .build());
        }
        return result;
    }


    @Override
    public PartnerNotificationDto getPartnerNotificationDetails(String partnerUUID,String partnerNotificationUUID) {
        PartnerNotificationDao partnerNotificationDao = partnerNotificationRepository.findByUUID(partnerNotificationUUID);
        if (partnerNotificationDao == null)
            throw new ResponseException("No partnerNotification  found for Id :" + partnerNotificationUUID);
        return PartnerNotificationDto.builder()
                .uuid(partnerNotificationDao.getUuid()).build();
    }

}
