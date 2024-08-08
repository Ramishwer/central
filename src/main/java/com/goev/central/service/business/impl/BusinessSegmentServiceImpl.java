package com.goev.central.service.business.impl;

import com.goev.central.dao.business.BusinessSegmentDao;
import com.goev.central.dao.business.BusinessSegmentMappingDao;
import com.goev.central.dao.partner.detail.PartnerSegmentDao;
import com.goev.central.dao.vehicle.detail.VehicleSegmentDao;
import com.goev.central.dto.business.BusinessSegmentDto;
import com.goev.central.dto.business.BusinessSegmentMappingDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import com.goev.central.repository.business.BusinessSegmentMappingRepository;
import com.goev.central.repository.business.BusinessSegmentRepository;
import com.goev.central.repository.partner.detail.PartnerSegmentRepository;
import com.goev.central.repository.vehicle.detail.VehicleSegmentRepository;
import com.goev.central.service.business.BusinessSegmentService;
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
public class BusinessSegmentServiceImpl implements BusinessSegmentService {

    private final BusinessSegmentRepository businessSegmentRepository;
    private final BusinessSegmentMappingRepository businessSegmentMappingRepository;
    private final PartnerSegmentRepository partnerSegmentRepository;
    private final VehicleSegmentRepository vehicleSegmentRepository;


    @Override
    public PaginatedResponseDto<BusinessSegmentDto> getSegments() {
        PaginatedResponseDto<BusinessSegmentDto> result = PaginatedResponseDto.<BusinessSegmentDto>builder().elements(new ArrayList<>()).build();
        List<BusinessSegmentDao> businessSegmentDaos = businessSegmentRepository.findAllActive();
        if (CollectionUtils.isEmpty(businessSegmentDaos))
            return result;

        for (BusinessSegmentDao businessSegmentDao : businessSegmentDaos) {
            result.getElements().add(getBusinessSegmentDto(businessSegmentDao));
        }
        return result;
    }

    private BusinessSegmentDto getBusinessSegmentDto(BusinessSegmentDao businessSegmentDao) {
        return BusinessSegmentDto.builder()
                .name(businessSegmentDao.getName())
                .description(businessSegmentDao.getDescription())
                .uuid(businessSegmentDao.getUuid())
                .build();
    }

    @Override
    public BusinessSegmentDto createSegment(BusinessSegmentDto businessSegmentDto) {

        BusinessSegmentDao businessSegmentDao = getBusinessSegmentDao(businessSegmentDto);
        businessSegmentDao = businessSegmentRepository.save(businessSegmentDao);
        if (businessSegmentDao == null)
            throw new ResponseException("Error in saving business segment");
        return getBusinessSegmentDto(businessSegmentDao);
    }

    private BusinessSegmentDao getBusinessSegmentDao(BusinessSegmentDto businessSegmentDto) {
        BusinessSegmentDao businessSegmentDao = new BusinessSegmentDao();
        businessSegmentDao.setName(businessSegmentDto.getName());
        businessSegmentDao.setDescription(businessSegmentDto.getDescription());
        return businessSegmentDao;
    }

    @Override
    public BusinessSegmentDto updateSegment(String segmentUUID, BusinessSegmentDto businessSegmentDto) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        BusinessSegmentDao newBusinessSegmentDao = getBusinessSegmentDao(businessSegmentDto);

        newBusinessSegmentDao.setId(businessSegmentDao.getId());
        newBusinessSegmentDao.setUuid(businessSegmentDao.getUuid());
        businessSegmentDao = businessSegmentRepository.update(newBusinessSegmentDao);
        if (businessSegmentDao == null)
            throw new ResponseException("Error in updating details business segment");
        return getBusinessSegmentDto(businessSegmentDao);
    }

    @Override
    public BusinessSegmentDto getSegmentDetails(String segmentUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        return getBusinessSegmentDto(businessSegmentDao);
    }

    @Override
    public Boolean deleteSegment(String segmentUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        businessSegmentRepository.delete(businessSegmentDao.getId());
        return true;
    }

    @Override
    public List<BusinessSegmentMappingDto> getVehicleSegmentMappings(String segmentUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);

        List<BusinessSegmentMappingDao> businessSegmentMappingDaoList = businessSegmentMappingRepository.findAllVehicleSegmentBySegmentId(businessSegmentDao.getId());

        List<BusinessSegmentMappingDto> result = new ArrayList<>();

        for (BusinessSegmentMappingDao businessSegmentMappingDao : businessSegmentMappingDaoList) {
            VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findById(businessSegmentMappingDao.getVehicleSegmentId());
            if (vehicleSegmentDao == null)
                continue;
            result.add(BusinessSegmentMappingDto.fromDao(businessSegmentMappingDao, BusinessSegmentDto.fromDao(businessSegmentDao), VehicleSegmentDto.fromDao(vehicleSegmentDao), null));
        }
        return result;
    }

    @Override
    public BusinessSegmentMappingDto createVehicleSegmentMapping(String segmentUUID, BusinessSegmentMappingDto businessSegmentMappingDto) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);

        if (businessSegmentMappingDto.getVehicleSegment() == null)
            throw new ResponseException("No vehicle segment details present.");

        VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findByUUID(businessSegmentMappingDto.getVehicleSegment().getUuid());

        if (vehicleSegmentDao == null)
            throw new ResponseException("No vehicle segment found for Id :" + businessSegmentMappingDto.getVehicleSegment().getUuid());

        BusinessSegmentMappingDao mappingDao = new BusinessSegmentMappingDao();

        mappingDao.setVehicleSegmentId(vehicleSegmentDao.getId());
        mappingDao.setBusinessSegmentId(businessSegmentDao.getId());
        mappingDao = businessSegmentMappingRepository.save(mappingDao);

        return BusinessSegmentMappingDto.fromDao(mappingDao, BusinessSegmentDto.fromDao(businessSegmentDao), VehicleSegmentDto.fromDao(vehicleSegmentDao), null);

    }

    @Override
    public Boolean deleteVehicleSegmentMapping(String segmentUUID, String vehicleSegmentMappingUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        BusinessSegmentMappingDao businessSegmentMappingDao = businessSegmentMappingRepository.findByUUID(vehicleSegmentMappingUUID);
        if (businessSegmentMappingDao == null)
            throw new ResponseException("No partner segment Mapping found for Id :" + vehicleSegmentMappingUUID);
        businessSegmentMappingRepository.delete(businessSegmentMappingDao.getId());
        return true;
    }

    @Override
    public List<BusinessSegmentMappingDto> getPartnerSegmentMappings(String segmentUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);

        List<BusinessSegmentMappingDao> businessSegmentMappingDaoList = businessSegmentMappingRepository.findAllPartnerSegmentBySegmentId(businessSegmentDao.getId());

        List<BusinessSegmentMappingDto> result = new ArrayList<>();

        for (BusinessSegmentMappingDao businessSegmentMappingDao : businessSegmentMappingDaoList) {
            PartnerSegmentDao partnerSegmentDao = partnerSegmentRepository.findById(businessSegmentMappingDao.getPartnerSegmentId());
            if (partnerSegmentDao == null)
                continue;
            result.add(BusinessSegmentMappingDto.fromDao(businessSegmentMappingDao, BusinessSegmentDto.fromDao(businessSegmentDao), null, PartnerSegmentDto.fromDao(partnerSegmentDao)));
        }
        return result;
    }

    @Override
    public BusinessSegmentMappingDto createPartnerSegmentMapping(String segmentUUID, BusinessSegmentMappingDto businessSegmentMappingDto) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);

        if (businessSegmentMappingDto.getPartnerSegment() == null)
            throw new ResponseException("No partner segment details present.");

        PartnerSegmentDao partnerSegmentDao = partnerSegmentRepository.findByUUID(businessSegmentMappingDto.getPartnerSegment().getUuid());

        if (partnerSegmentDao == null)
            throw new ResponseException("No partner segment found for Id :" + businessSegmentMappingDto.getPartnerSegment().getUuid());

        BusinessSegmentMappingDao mappingDao = new BusinessSegmentMappingDao();

        mappingDao.setPartnerSegmentId(partnerSegmentDao.getId());
        mappingDao.setBusinessSegmentId(businessSegmentDao.getId());
        mappingDao = businessSegmentMappingRepository.save(mappingDao);

        return BusinessSegmentMappingDto.fromDao(mappingDao, BusinessSegmentDto.fromDao(businessSegmentDao), null, PartnerSegmentDto.fromDao(partnerSegmentDao));

    }

    @Override
    public Boolean deletePartnerSegmentMapping(String segmentUUID, String partnerSegmentMappingUUID) {
        BusinessSegmentDao businessSegmentDao = businessSegmentRepository.findByUUID(segmentUUID);
        if (businessSegmentDao == null)
            throw new ResponseException("No business segment found for Id :" + segmentUUID);
        BusinessSegmentMappingDao businessSegmentMappingDao = businessSegmentMappingRepository.findByUUID(partnerSegmentMappingUUID);
        if (businessSegmentMappingDao == null)
            throw new ResponseException("No partner segment Mapping found for Id :" + partnerSegmentMappingUUID);
        businessSegmentMappingRepository.delete(businessSegmentMappingDao.getId());
        return true;
    }
}
