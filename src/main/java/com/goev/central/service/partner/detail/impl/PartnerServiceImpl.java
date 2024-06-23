package com.goev.central.service.partner.detail.impl;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.detail.PartnerService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public PaginatedResponseDto<PartnerDto> getPartnerStatuses(String status) {
        PaginatedResponseDto<PartnerDto> result = PaginatedResponseDto.<PartnerDto>builder().elements(new ArrayList<>()).build();
        List<PartnerDao> partners = null;
        if (PartnerStatus.OFF_DUTY.name().equals(status))
            partners = partnerRepository.findAllByStatusAndShiftIdNotNull(Collections.singletonList(status));
        else if (PartnerStatus.ON_DUTY.name().equals(status))
            partners = partnerRepository.findAllByStatus(Arrays.asList(PartnerStatus.ON_DUTY.name(), PartnerStatus.VEHICLE_ASSIGNED.name(),
                    PartnerStatus.CHECKLIST.name(), PartnerStatus.RETURN_CHECKLIST.name()));
        else if (PartnerStatus.ONLINE.name().equals(status))
            partners = partnerRepository.findAllByStatus(Arrays.asList(PartnerStatus.ONLINE.name(), PartnerStatus.ON_BOOKING.name()));
        return getPartnerDtoPaginatedResponseDto(partners, result);
    }

    @Override
    public PartnerDto getPartnerStatus(String partnerUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setUuid(partner.getUuid());
        partnerDto.setPunchId(partner.getPunchId());
        partnerDto.setStatus(partner.getStatus());
        partnerDto.setSubStatus(partner.getSubStatus());
        partnerDto.setLocationStatus(partner.getLocationStatus());

        partnerDto.setVehicleDetails(ApplicationConstants.GSON.fromJson(partner.getVehicleDetails(), VehicleViewDto.class));
        partnerDto.setDutyDetails(ApplicationConstants.GSON.fromJson(partner.getDutyDetails(), PartnerDutyDto.class));
        partnerDto.setBookingDetails(ApplicationConstants.GSON.fromJson(partner.getBookingDetails(), BookingViewDto.class));
        partnerDto.setLocationDetails(ApplicationConstants.GSON.fromJson(partner.getLocationDetails(), LocationDto.class));
        partnerDto.setPartnerDetails(PartnerViewDto.fromDao(partner));
        return partnerDto;
    }


    private PaginatedResponseDto<PartnerDto> getPartnerDtoPaginatedResponseDto(List<PartnerDao> partners, PaginatedResponseDto<PartnerDto> result) {
        if (CollectionUtils.isEmpty(partners))
            return result;
        for (PartnerDao partner : partners) {
            PartnerDto partnerDto = new PartnerDto();
            partnerDto.setUuid(partner.getUuid());
            partnerDto.setPunchId(partner.getPunchId());
            partnerDto.setStatus(partner.getStatus());
            partnerDto.setSubStatus(partner.getSubStatus());
            partnerDto.setLocationStatus(partner.getLocationStatus());

            partnerDto.setVehicleDetails(ApplicationConstants.GSON.fromJson(partner.getVehicleDetails(), VehicleViewDto.class));
            partnerDto.setDutyDetails(ApplicationConstants.GSON.fromJson(partner.getDutyDetails(), PartnerDutyDto.class));
            partnerDto.setBookingDetails(ApplicationConstants.GSON.fromJson(partner.getBookingDetails(), BookingViewDto.class));
            partnerDto.setLocationDetails(ApplicationConstants.GSON.fromJson(partner.getLocationDetails(), LocationDto.class));
            partnerDto.setPartnerDetails(PartnerViewDto.fromDao(partner));
            result.getElements().add(partnerDto);
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<PartnerViewDto> getPartners() {

        PaginatedResponseDto<PartnerViewDto> result = PaginatedResponseDto.<PartnerViewDto>builder().elements(new ArrayList<>()).build();
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
        PaginatedResponseDto<PartnerViewDto> result = PaginatedResponseDto.<PartnerViewDto>builder().elements(new ArrayList<>()).build();
        List<PartnerDao> partners = partnerRepository.findAllByOnboardingStatus(onboardingStatus);
        return getPartnerViewDtoPaginatedResponseDto(partners, result);
    }

}
