package com.goev.central.service.partner.duty.impl;

import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.duty.PartnerDutyService;
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
public class PartnerDutyServiceImpl implements PartnerDutyService {

    private final PartnerDutyRepository partnerDutyRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerDutyDto> getDuties(String partnerUUID) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerDutyDao> activeDuties = partnerDutyRepository.findAllByPartnerId(partner.getId());
        if (CollectionUtils.isEmpty(activeDuties))
            return PaginatedResponseDto.<PartnerDutyDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();

        List<PartnerDutyDto> dutyList = new ArrayList<>();
        activeDuties.forEach(x -> dutyList.add(PartnerDutyDto.builder()
                .uuid(x.getUuid())
                .build()));
        return PaginatedResponseDto.<PartnerDutyDto>builder().elements(dutyList).build();

    }

    @Override
    public PartnerDutyDto createDuty(String partnerUUID, PartnerDutyDto partnerDutyDto) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDutyDao partnerDutyDao = new PartnerDutyDao();

        partnerDutyDao.setPartnerId(partner.getId());
        partnerDutyDao = partnerDutyRepository.save(partnerDutyDao);
        if (partnerDutyDao == null)
            throw new ResponseException("Error in saving partner duty");
        return PartnerDutyDto.builder()
                .uuid(partnerDutyDao.getUuid())
                .build();
    }

    @Override
    public PartnerDutyDto updateDuty(String partnerUUID, String dutyUUID, PartnerDutyDto partnerDutyDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDutyDao partnerDutyDao = partnerDutyRepository.findByUUID(dutyUUID);
        if (partnerDutyDao == null)
            throw new ResponseException("No partner duty found for Id :" + dutyUUID);

        PartnerDutyDao newPartnerDutyDao = new PartnerDutyDao();
        newPartnerDutyDao.setPartnerId(partner.getId());

        newPartnerDutyDao.setId(partnerDutyDao.getId());
        newPartnerDutyDao.setUuid(partnerDutyDao.getUuid());
        partnerDutyDao = partnerDutyRepository.update(newPartnerDutyDao);
        if (partnerDutyDao == null)
            throw new ResponseException("Error in updating details partner duty");


        return PartnerDutyDto.builder()
                .uuid(partnerDutyDao.getUuid())
                .build();
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

    @Override
    public Boolean deleteDuty(String partnerUUID, String dutyUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerDutyDao partnerDutyDao = partnerDutyRepository.findByUUID(dutyUUID);
        if (partnerDutyDao == null)
            throw new ResponseException("No partner duty found for Id :" + dutyUUID);
        partnerDutyRepository.delete(partnerDutyDao.getId());
        return true;
    }
}
