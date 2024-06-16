package com.goev.central.service.partner.duty.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.central.service.partner.duty.PartnerDutyService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerDutyServiceImpl implements PartnerDutyService {

    private final PartnerDutyRepository partnerDutyRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerDutyDto> getDuties() {


//        List<PartnerDutyDao> activeDuties = partnerDutyRepository.findAllByPartnerId(partner.getId());
//        if (CollectionUtils.isEmpty(activeDuties))
        return PaginatedResponseDto.<PartnerDutyDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();

//        List<PartnerDutyDto> dutyList = new ArrayList<>();
//        activeDuties.forEach(x -> dutyList.add(PartnerDutyDto.builder()
//                .uuid(x.getUuid())
//                .build()));
//        return PaginatedResponseDto.<PartnerDutyDto>builder().elements(dutyList).build();

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
