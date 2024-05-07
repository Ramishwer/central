package com.goev.central.service.partner.duty.impl;

import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.duty.PartnerShiftService;
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
public class PartnerShiftServiceImpl implements PartnerShiftService {

    private final PartnerShiftRepository partnerShiftRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerShiftDto> getShifts(String partnerUUID) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerShiftDao> activeShifts = partnerShiftRepository.findAllByPartnerId(partner.getId());
        if (CollectionUtils.isEmpty(activeShifts))
            return PaginatedResponseDto.<PartnerShiftDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();

        List<PartnerShiftDto> shiftList = new ArrayList<>();
        activeShifts.forEach(x -> shiftList.add(PartnerShiftDto.builder()
                .uuid(x.getUuid())
                .build()));
        return PaginatedResponseDto.<PartnerShiftDto>builder().elements(shiftList).build();

    }

    @Override
    public PartnerShiftDto createShift(String partnerUUID, PartnerShiftDto partnerShiftDto) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = new PartnerShiftDao();

        partnerShiftDao.setPartnerId(partner.getId());
        partnerShiftDao = partnerShiftRepository.save(partnerShiftDao);
        if (partnerShiftDao == null)
            throw new ResponseException("Error in saving partner shift");
        return PartnerShiftDto.builder()
                .uuid(partnerShiftDao.getUuid())
                .build();
    }

    @Override
    public PartnerShiftDto updateShift(String partnerUUID, String shiftUUID, PartnerShiftDto partnerShiftDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByUUID(shiftUUID);
        if (partnerShiftDao == null)
            throw new ResponseException("No partner shift found for Id :" + shiftUUID);

        PartnerShiftDao newPartnerShiftDao = new PartnerShiftDao();
        newPartnerShiftDao.setPartnerId(partner.getId());

        newPartnerShiftDao.setId(partnerShiftDao.getId());
        newPartnerShiftDao.setUuid(partnerShiftDao.getUuid());
        partnerShiftDao = partnerShiftRepository.update(newPartnerShiftDao);
        if (partnerShiftDao == null)
            throw new ResponseException("Error in updating details partner shift");


        return PartnerShiftDto.builder()
                .uuid(partnerShiftDao.getUuid())
                .build();
    }

    @Override
    public PartnerShiftDto getShiftDetails(String partnerUUID, String shiftUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByUUID(shiftUUID);
        if (partnerShiftDao == null)
            throw new ResponseException("No partner shift found for Id :" + shiftUUID);


        return PartnerShiftDto.builder()
                .uuid(partnerShiftDao.getUuid())
                .build();
    }

    @Override
    public Boolean deleteShift(String partnerUUID, String shiftUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByUUID(shiftUUID);
        if (partnerShiftDao == null)
            throw new ResponseException("No partner shift found for Id :" + shiftUUID);
        partnerShiftRepository.delete(partnerShiftDao.getId());
        return true;
    }
}
