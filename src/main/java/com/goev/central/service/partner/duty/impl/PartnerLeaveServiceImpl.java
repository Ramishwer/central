package com.goev.central.service.partner.duty.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerLeaveDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerLeaveDto;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerLeaveRepository;
import com.goev.central.service.partner.duty.PartnerLeaveService;
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
public class PartnerLeaveServiceImpl implements PartnerLeaveService {

    private final PartnerLeaveRepository partnerLeaveRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<PartnerLeaveDto> getLeaves(String partnerUUID) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        List<PartnerLeaveDao> activeLeaves = partnerLeaveRepository.findAllByPartnerId(partner.getId());
        if (CollectionUtils.isEmpty(activeLeaves))
            return PaginatedResponseDto.<PartnerLeaveDto>builder().elements(new ArrayList<>()).build();

        List<PartnerLeaveDto> leaveList = new ArrayList<>();
        activeLeaves.forEach(x -> leaveList.add(PartnerLeaveDto.builder()
                .uuid(x.getUuid())
                .build()));
        return PaginatedResponseDto.<PartnerLeaveDto>builder().elements(leaveList).build();

    }

    @Override
    public PartnerLeaveDto createLeave(String partnerUUID, PartnerLeaveDto partnerLeaveDto) {

        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerLeaveDao partnerLeaveDao = new PartnerLeaveDao();

        partnerLeaveDao.setPartnerId(partner.getId());
        partnerLeaveDao = partnerLeaveRepository.save(partnerLeaveDao);
        if (partnerLeaveDao == null)
            throw new ResponseException("Error in saving partner leave");
        return PartnerLeaveDto.builder()
                .uuid(partnerLeaveDao.getUuid())
                .build();
    }

    @Override
    public PartnerLeaveDto updateLeave(String partnerUUID, String leaveUUID, PartnerLeaveDto partnerLeaveDto) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerLeaveDao partnerLeaveDao = partnerLeaveRepository.findByUUID(leaveUUID);
        if (partnerLeaveDao == null)
            throw new ResponseException("No partner leave found for Id :" + leaveUUID);

        PartnerLeaveDao newPartnerLeaveDao = new PartnerLeaveDao();
        newPartnerLeaveDao.setPartnerId(partner.getId());

        newPartnerLeaveDao.setId(partnerLeaveDao.getId());
        newPartnerLeaveDao.setUuid(partnerLeaveDao.getUuid());
        partnerLeaveDao = partnerLeaveRepository.update(newPartnerLeaveDao);
        if (partnerLeaveDao == null)
            throw new ResponseException("Error in updating details partner leave");


        return PartnerLeaveDto.builder()
                .uuid(partnerLeaveDao.getUuid())
                .build();
    }

    @Override
    public PartnerLeaveDto getLeaveDetails(String partnerUUID, String leaveUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerLeaveDao partnerLeaveDao = partnerLeaveRepository.findByUUID(leaveUUID);
        if (partnerLeaveDao == null)
            throw new ResponseException("No partner leave found for Id :" + leaveUUID);


        return PartnerLeaveDto.builder()
                .uuid(partnerLeaveDao.getUuid())
                .build();
    }

    @Override
    public Boolean deleteLeave(String partnerUUID, String leaveUUID) {
        PartnerDao partner = partnerRepository.findByUUID(partnerUUID);
        if (partner == null)
            throw new ResponseException("No partner found for Id :" + partnerUUID);

        PartnerLeaveDao partnerLeaveDao = partnerLeaveRepository.findByUUID(leaveUUID);
        if (partnerLeaveDao == null)
            throw new ResponseException("No partner leave found for Id :" + leaveUUID);
        partnerLeaveRepository.delete(partnerLeaveDao.getId());
        return true;
    }
}
