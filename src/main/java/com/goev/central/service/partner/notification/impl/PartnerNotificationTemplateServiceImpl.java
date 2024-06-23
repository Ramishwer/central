package com.goev.central.service.partner.notification.impl;

import com.goev.central.dao.partner.notification.PartnerNotificationTemplateDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.notification.PartnerNotificationTemplateDto;
import com.goev.central.repository.partner.notification.PartnerNotificationTemplateRepository;
import com.goev.central.service.partner.notification.PartnerNotificationTemplateService;
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
public class PartnerNotificationTemplateServiceImpl implements PartnerNotificationTemplateService {

    private final PartnerNotificationTemplateRepository partnerNotificationTemplateRepository;

    @Override
    public PaginatedResponseDto<PartnerNotificationTemplateDto> getPartnerNotificationTemplates() {
        PaginatedResponseDto<PartnerNotificationTemplateDto> result = PaginatedResponseDto.<PartnerNotificationTemplateDto>builder().elements(new ArrayList<>()).build();
        List<PartnerNotificationTemplateDao> partnerNotificationTemplateDaos = partnerNotificationTemplateRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerNotificationTemplateDaos))
            return result;

        for (PartnerNotificationTemplateDao partnerNotificationTemplateDao : partnerNotificationTemplateDaos) {
            result.getElements().add(PartnerNotificationTemplateDto.builder()
                    .uuid(partnerNotificationTemplateDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerNotificationTemplateDto createPartnerNotificationTemplate(PartnerNotificationTemplateDto partnerNotificationTemplateDto) {

        PartnerNotificationTemplateDao partnerNotificationTemplateDao = new PartnerNotificationTemplateDao();
        partnerNotificationTemplateDao = partnerNotificationTemplateRepository.save(partnerNotificationTemplateDao);
        if (partnerNotificationTemplateDao == null)
            throw new ResponseException("Error in saving partnerNotificationTemplate partnerNotificationTemplate");
        return PartnerNotificationTemplateDto.builder()
                .uuid(partnerNotificationTemplateDao.getUuid()).build();
    }

    @Override
    public PartnerNotificationTemplateDto updatePartnerNotificationTemplate(String partnerNotificationTemplateUUID, PartnerNotificationTemplateDto partnerNotificationTemplateDto) {
        PartnerNotificationTemplateDao partnerNotificationTemplateDao = partnerNotificationTemplateRepository.findByUUID(partnerNotificationTemplateUUID);
        if (partnerNotificationTemplateDao == null)
            throw new ResponseException("No partnerNotificationTemplate  found for Id :" + partnerNotificationTemplateUUID);
        PartnerNotificationTemplateDao newPartnerNotificationTemplateDao = new PartnerNotificationTemplateDao();


        newPartnerNotificationTemplateDao.setId(partnerNotificationTemplateDao.getId());
        newPartnerNotificationTemplateDao.setUuid(partnerNotificationTemplateDao.getUuid());
        partnerNotificationTemplateDao = partnerNotificationTemplateRepository.update(newPartnerNotificationTemplateDao);
        if (partnerNotificationTemplateDao == null)
            throw new ResponseException("Error in updating details partnerNotificationTemplate ");
        return PartnerNotificationTemplateDto.builder()
                .uuid(partnerNotificationTemplateDao.getUuid()).build();
    }

    @Override
    public PartnerNotificationTemplateDto getPartnerNotificationTemplateDetails(String partnerNotificationTemplateUUID) {
        PartnerNotificationTemplateDao partnerNotificationTemplateDao = partnerNotificationTemplateRepository.findByUUID(partnerNotificationTemplateUUID);
        if (partnerNotificationTemplateDao == null)
            throw new ResponseException("No partnerNotificationTemplate  found for Id :" + partnerNotificationTemplateUUID);
        return PartnerNotificationTemplateDto.builder()
                .uuid(partnerNotificationTemplateDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerNotificationTemplate(String partnerNotificationTemplateUUID) {
        PartnerNotificationTemplateDao partnerNotificationTemplateDao = partnerNotificationTemplateRepository.findByUUID(partnerNotificationTemplateUUID);
        if (partnerNotificationTemplateDao == null)
            throw new ResponseException("No partnerNotificationTemplate  found for Id :" + partnerNotificationTemplateUUID);
        partnerNotificationTemplateRepository.delete(partnerNotificationTemplateDao.getId());
        return true;
    }
}
