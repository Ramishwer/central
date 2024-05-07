package com.goev.central.service.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDeviceDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerDeviceDto;
import com.goev.central.repository.partner.detail.PartnerDeviceRepository;
import com.goev.central.service.partner.detail.PartnerDeviceService;
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
public class PartnerDeviceServiceImpl implements PartnerDeviceService {

    private final PartnerDeviceRepository partnerDeviceRepository;

    @Override
    public PaginatedResponseDto<PartnerDeviceDto> getPartnerDevices(String partnerUUID) {
        PaginatedResponseDto<PartnerDeviceDto> result = PaginatedResponseDto.<PartnerDeviceDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerDeviceDao> partnerDeviceDaos = partnerDeviceRepository.findAll();
        if (CollectionUtils.isEmpty(partnerDeviceDaos))
            return result;

        for (PartnerDeviceDao partnerDeviceDao : partnerDeviceDaos) {
            result.getElements().add(PartnerDeviceDto.builder()
                    .uuid(partnerDeviceDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerDeviceDto getPartnerDeviceDetails(String partnerUUID, String partnerDeviceUUID) {
        PartnerDeviceDao partnerDeviceDao = partnerDeviceRepository.findByUUID(partnerDeviceUUID);
        if (partnerDeviceDao == null)
            throw new ResponseException("No partnerDevice  found for Id :" + partnerDeviceUUID);
        return PartnerDeviceDto.builder()
                .uuid(partnerDeviceDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerDevice(String partnerUUID, String partnerDeviceUUID) {
        PartnerDeviceDao partnerDeviceDao = partnerDeviceRepository.findByUUID(partnerDeviceUUID);
        if (partnerDeviceDao == null)
            throw new ResponseException("No partnerDevice  found for Id :" + partnerDeviceUUID);
        partnerDeviceRepository.delete(partnerDeviceDao.getId());
        return true;
    }
}
