package com.goev.central.service.partner.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.detail.PartnerService;
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
public class PartnerServiceImpl implements PartnerService {


    private final PartnerRepository partnerRepository;

    @Override
    public Boolean deletePartner(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);
        partnerRepository.delete(partner.getId());
        return true;
    }

    @Override
    public PaginatedResponseDto<PartnerDto> getPartnerStatus(String status) {

        PaginatedResponseDto<PartnerDto> result = PaginatedResponseDto.<PartnerDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();

        List<PartnerDao> partners = partnerRepository.findAllByStatus(status);

        return getPartnerDtoPaginatedResponseDto(partners, result);
    }


    private PaginatedResponseDto<PartnerDto> getPartnerDtoPaginatedResponseDto(List<PartnerDao> partners, PaginatedResponseDto<PartnerDto> result) {
        if (CollectionUtils.isEmpty(partners))
            return result;
        for (PartnerDao partner : partners) {
            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setUuid(partner.getUuid());
            partnerDto.setPunchId(partner.getPunchId());
            partnerDto.setStatus(partner.getStatus());
            partnerDto.setVehicleDetails(ApplicationConstants.GSON.fromJson(partner.getVehicleDetails(), VehicleViewDto.class));
            partnerDto.setSubStatus(partner.getSubStatus());
            result.getElements().add(partnerDto);
        }
        return result;
    }
    @Override
    public PaginatedResponseDto<PartnerViewDto> getPartners() {

        PaginatedResponseDto<PartnerViewDto> result = PaginatedResponseDto.<PartnerViewDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerDao> partners = partnerRepository.findAllActive();
        return getPartnerViewDtoPaginatedResponseDto(partners, result);
    }

    private PaginatedResponseDto<PartnerViewDto> getPartnerViewDtoPaginatedResponseDto(List<PartnerDao> partners, PaginatedResponseDto<PartnerViewDto> result) {
        if (CollectionUtils.isEmpty(partners))
            return result;
        for (PartnerDao partner : partners) {
            PartnerViewDto partnerViewDto = ApplicationConstants.GSON.fromJson(partner.getViewInfo(), PartnerViewDto.class);
            if (partnerViewDto == null)
                continue;
            partnerViewDto.setUuid(partner.getUuid());
            result.getElements().add(partnerViewDto);
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<PartnerViewDto> getPartners(String onboardingStatus) {
        PaginatedResponseDto<PartnerViewDto> result = PaginatedResponseDto.<PartnerViewDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerDao> partners = partnerRepository.findAllByOnboardingStatus(onboardingStatus);
        return getPartnerViewDtoPaginatedResponseDto(partners, result);
    }

}
