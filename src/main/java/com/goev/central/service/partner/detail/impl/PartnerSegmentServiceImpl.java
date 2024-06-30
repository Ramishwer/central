package com.goev.central.service.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerSegmentDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.repository.partner.detail.PartnerSegmentRepository;
import com.goev.central.service.partner.detail.PartnerSegmentService;
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
public class PartnerSegmentServiceImpl implements PartnerSegmentService {

    private final PartnerSegmentRepository partnerSegmentRepository;

    @Override
    public PaginatedResponseDto<PartnerSegmentDto> getSegments() {
        PaginatedResponseDto<PartnerSegmentDto> result = PaginatedResponseDto.<PartnerSegmentDto>builder().elements(new ArrayList<>()).build();
        List<PartnerSegmentDao> partnerSegmentDaos = partnerSegmentRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerSegmentDaos))
            return result;

        for (PartnerSegmentDao partnerSegmentDao : partnerSegmentDaos) {
            result.getElements().add(getPartnerSegmentDto(partnerSegmentDao));
        }
        return result;
    }

    private PartnerSegmentDto getPartnerSegmentDto(PartnerSegmentDao partnerSegmentDao) {
        return PartnerSegmentDto.builder()
                .name(partnerSegmentDao.getName())
                .description(partnerSegmentDao.getDescription())
                .uuid(partnerSegmentDao.getUuid())
                .build();
    }

    @Override
    public PartnerSegmentDto createSegment(PartnerSegmentDto partnerSegmentDto) {

        PartnerSegmentDao partnerSegmentDao = getPartnerSegmentDao(partnerSegmentDto);
        partnerSegmentDao = partnerSegmentRepository.save(partnerSegmentDao);
        if (partnerSegmentDao == null)
            throw new ResponseException("Error in saving partner segment");
        return getPartnerSegmentDto(partnerSegmentDao);
    }

    private PartnerSegmentDao getPartnerSegmentDao(PartnerSegmentDto partnerSegmentDto) {
        PartnerSegmentDao partnerSegmentDao = new PartnerSegmentDao();
        partnerSegmentDao.setName(partnerSegmentDto.getName());
        partnerSegmentDao.setDescription(partnerSegmentDto.getDescription());
        return partnerSegmentDao;
    }

    @Override
    public PartnerSegmentDto updateSegment(String segmentUUID, PartnerSegmentDto partnerSegmentDto) {
        PartnerSegmentDao partnerSegmentDao = partnerSegmentRepository.findByUUID(segmentUUID);
        if (partnerSegmentDao == null)
            throw new ResponseException("No partner segment found for Id :" + segmentUUID);
        PartnerSegmentDao newPartnerSegmentDao = getPartnerSegmentDao(partnerSegmentDto);

        newPartnerSegmentDao.setId(partnerSegmentDao.getId());
        newPartnerSegmentDao.setUuid(partnerSegmentDao.getUuid());
        partnerSegmentDao = partnerSegmentRepository.update(newPartnerSegmentDao);
        if (partnerSegmentDao == null)
            throw new ResponseException("Error in updating details partner segment");
        return getPartnerSegmentDto(partnerSegmentDao);
    }

    @Override
    public PartnerSegmentDto getSegmentDetails(String segmentUUID) {
        PartnerSegmentDao partnerSegmentDao = partnerSegmentRepository.findByUUID(segmentUUID);
        if (partnerSegmentDao == null)
            throw new ResponseException("No partner segment found for Id :" + segmentUUID);
        return getPartnerSegmentDto(partnerSegmentDao);
    }

    @Override
    public Boolean deleteSegment(String segmentUUID) {
        PartnerSegmentDao partnerSegmentDao = partnerSegmentRepository.findByUUID(segmentUUID);
        if (partnerSegmentDao == null)
            throw new ResponseException("No partner segment found for Id :" + segmentUUID);
        partnerSegmentRepository.delete(partnerSegmentDao.getId());
        return true;
    }
}
