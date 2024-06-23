package com.goev.central.service.partner.duty.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.service.partner.duty.PartnerShiftService;
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
public class PartnerShiftServiceImpl implements PartnerShiftService {

    private final PartnerShiftRepository partnerShiftRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerShiftDto> getShifts(String status, PageDto page) {

        List<PartnerShiftDao> activeShifts = partnerShiftRepository.findAllByStatus(status,page);
        if (CollectionUtils.isEmpty(activeShifts))
            return PaginatedResponseDto.<PartnerShiftDto>builder().elements(new ArrayList<>()).build();

        List<PartnerDao> partners = partnerRepository.findAllByIds(activeShifts.stream().map(PartnerShiftDao::getPartnerId).toList());
        Map<Integer, PartnerDao> partnerDaoMap = partners.stream().collect(Collectors.toMap(PartnerDao::getId, Function.identity()));

        List<PartnerShiftDto> shiftList = new ArrayList<>();
        activeShifts.forEach(x -> {
            PartnerViewDto partnerViewDto = PartnerViewDto.fromDao(partnerDaoMap.get(x.getPartnerId()));
            shiftList.add(PartnerShiftDto.fromDao(x, partnerViewDto));
        });
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
        return PartnerShiftDto.fromDao(partnerShiftDao, PartnerViewDto.fromDao(partner));
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


        return PartnerShiftDto.fromDao(partnerShiftDao, PartnerViewDto.fromDao(partner));
    }

    @Override
    public PartnerShiftDto getShiftDetails(String partnerUUID, String shiftUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByUUID(shiftUUID);
        if (partnerShiftDao == null)
            throw new ResponseException("No partner shift found for Id :" + shiftUUID);


        return PartnerShiftDto.fromDao(partnerShiftDao, PartnerViewDto.fromDao(partner));
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
