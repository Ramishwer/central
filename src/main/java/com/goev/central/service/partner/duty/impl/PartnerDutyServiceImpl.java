package com.goev.central.service.partner.duty.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.enums.partner.PartnerDutyStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.service.partner.duty.PartnerDutyService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerDutyServiceImpl implements PartnerDutyService {

    private final PartnerDutyRepository partnerDutyRepository;
    private final PartnerRepository partnerRepository;
    private final PartnerShiftRepository partnerShiftRepository;

    @Override
    public PaginatedResponseDto<PartnerDutyDto> getDuties(String status, PageDto page, FilterDto filter) {
        List<PartnerDutyDao> activeDuties = null;

        if(PartnerDutyStatus.IN_PROGRESS.name().equals(status))
            activeDuties = partnerDutyRepository.findAllByStatus(status, page);
        else
            activeDuties = partnerDutyRepository.findAllByStatus(status, page,filter);
        if (CollectionUtils.isEmpty(activeDuties))
            return PaginatedResponseDto.<PartnerDutyDto>builder().elements(new ArrayList<>()).build();

        List<PartnerShiftDao> partnerShifts = partnerShiftRepository.findAllByIds(activeDuties.stream().map(PartnerDutyDao::getPartnerShiftId).toList());
        Map<Integer, PartnerShiftDao> partnerShiftDaoMap = partnerShifts.stream().collect(Collectors.toMap(PartnerShiftDao::getId, Function.identity()));

        List<PartnerDao> partners = partnerRepository.findAllByIds(activeDuties.stream().map(PartnerDutyDao::getPartnerId).toList());
        Map<Integer, PartnerDao> partnerDaoMap = partners.stream().collect(Collectors.toMap(PartnerDao::getId, Function.identity()));

        List<PartnerDutyDto> dutyList = new ArrayList<>();
        activeDuties.forEach(x -> {
            PartnerViewDto partnerViewDto = PartnerViewDto.fromDao(partnerDaoMap.get(x.getPartnerId()));
            PartnerShiftDto partnerShiftDto = PartnerShiftDto.fromDao(partnerShiftDaoMap.get(x.getPartnerShiftId()), partnerViewDto);
            dutyList.add(PartnerDutyDto.fromDao(x, partnerViewDto, partnerShiftDto));
        });

        return PaginatedResponseDto.<PartnerDutyDto>builder().elements(dutyList).build();

    }


    @Override
    public PartnerDutyDto getDutyDetails(String partnerUUID, String dutyUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDutyDao partnerDutyDao = partnerDutyRepository.findByUUID(dutyUUID);
        if (partnerDutyDao == null)
            throw new ResponseException("No partner duty found for Id :" + dutyUUID);


        return PartnerDutyDto.builder()
                .uuid(partnerDutyDao.getUuid())
                .build();
    }

}
