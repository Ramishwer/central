package com.goev.central.service.business.impl;

import com.goev.central.dao.business.BusinessSegmentDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.service.business.BusinessSegmentService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BusinessSegmentServiceImpl implements BusinessSegmentService {

    private final BusinessSegmentRepository businessSegmentRepository;

    @Override
    public PaginatedResponseDto<BusinessSegmentDto> getSegments() {
        PaginatedResponseDto<BusinessSegmentDto> result = PaginatedResponseDto.<BusinessSegmentDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<BusinessSegmentDao> businessSegmentDaos = businessSegmentRepository.findAll();
        if (CollectionUtils.isEmpty(businessSegmentDaos))
            return result;

        for (BusinessSegmentDao businessSegmentDao : businessSegmentDaos) {
            result.getElements().add(BusinessSegmentDto.builder()
                    .name(businessSegmentDao.getName())
                    .uuid(businessSegmentDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public BusinessSegmentDto createSegment(BusinessSegmentDto businessSegmentDto) {

        BusinessSegmentDao businessSegmentDao = new BusinessSegmentDao();
        businessSegmentDao.setName(businessSegmentDto.getName());
        businessSegmentDao = businessSegmentRepository.save(businessSegmentDao);
        if (businessSegmentDao == null)
            throw new ResponseException("Error in saving business segment");
        return BusinessSegmentDto.builder().name(businessSegmentDao.getName()).uuid(businessSegmentDao.getUuid()).build();
    }

    @Override
    public BusinessSegmentDto updateSegment(String segmentUUID, BusinessSegmentDto businessSegmentDto) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        BusinessSegmentDao newBusinessSegmentDao = new BusinessSegmentDao();
        newBusinessSegmentDao.setName(businessSegmentDto.getName());

        newBusinessSegmentDao.setId(businessSegmentDao.getId());
        newBusinessSegmentDao.setUuid(businessSegmentDao.getUuid());
        businessSegmentDao = businessSegmentRepository.update(newBusinessSegmentDao);
        if (businessSegmentDao == null)
            throw new ResponseException("Error in updating details business segment");
        return BusinessSegmentDto.builder()
                .name(businessSegmentDao.getName())
                .uuid(businessSegmentDao.getUuid()).build();
    }

    @Override
    public BusinessSegmentDto getSegmentDetails(String segmentUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        return BusinessSegmentDto.builder()
                .name(businessSegmentDao.getName())
                .uuid(businessSegmentDao.getUuid()).build();
    }

    @Override
    public Boolean deleteSegment(String segmentUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        businessSegmentRepository.delete(businessSegmentDao.getId());
        return true;
    }
}
