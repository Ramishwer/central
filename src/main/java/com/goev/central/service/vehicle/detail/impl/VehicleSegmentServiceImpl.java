package com.goev.central.service.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleSegmentDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import com.goev.central.repository.vehicle.detail.VehicleSegmentRepository;
import com.goev.central.service.vehicle.detail.VehicleSegmentService;
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
public class VehicleSegmentServiceImpl implements VehicleSegmentService {

    private final VehicleSegmentRepository vehicleSegmentRepository;

    @Override
    public PaginatedResponseDto<VehicleSegmentDto> getSegments() {
        PaginatedResponseDto<VehicleSegmentDto> result = PaginatedResponseDto.<VehicleSegmentDto>builder().elements(new ArrayList<>()).build();
        List<VehicleSegmentDao> vehicleSegmentDaos = vehicleSegmentRepository.findAllActive();
        if (CollectionUtils.isEmpty(vehicleSegmentDaos))
            return result;

        for (VehicleSegmentDao vehicleSegmentDao : vehicleSegmentDaos) {
            result.getElements().add(getVehicleSegmentDto(vehicleSegmentDao));
        }
        return result;
    }

    private VehicleSegmentDto getVehicleSegmentDto(VehicleSegmentDao vehicleSegmentDao) {
        return VehicleSegmentDto.builder()
                .name(vehicleSegmentDao.getName())
                .description(vehicleSegmentDao.getDescription())
                .uuid(vehicleSegmentDao.getUuid())
                .build();
    }

    @Override
    public VehicleSegmentDto createSegment(VehicleSegmentDto vehicleSegmentDto) {

        VehicleSegmentDao vehicleSegmentDao = getVehicleSegmentDao(vehicleSegmentDto);
        vehicleSegmentDao = vehicleSegmentRepository.save(vehicleSegmentDao);
        if (vehicleSegmentDao == null)
            throw new ResponseException("Error in saving vehicle segment");
        return getVehicleSegmentDto(vehicleSegmentDao);
    }

    private VehicleSegmentDao getVehicleSegmentDao(VehicleSegmentDto vehicleSegmentDto) {
        VehicleSegmentDao vehicleSegmentDao = new VehicleSegmentDao();
        vehicleSegmentDao.setName(vehicleSegmentDto.getName());
        vehicleSegmentDao.setDescription(vehicleSegmentDto.getDescription());
        return vehicleSegmentDao;
    }

    @Override
    public VehicleSegmentDto updateSegment(String segmentUUID, VehicleSegmentDto vehicleSegmentDto) {
        VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findByUUID(segmentUUID);
        if (vehicleSegmentDao == null)
            throw new ResponseException("No vehicle segment found for Id :" + segmentUUID);
        VehicleSegmentDao newVehicleSegmentDao = getVehicleSegmentDao(vehicleSegmentDto);

        newVehicleSegmentDao.setId(vehicleSegmentDao.getId());
        newVehicleSegmentDao.setUuid(vehicleSegmentDao.getUuid());
        vehicleSegmentDao = vehicleSegmentRepository.update(newVehicleSegmentDao);
        if (vehicleSegmentDao == null)
            throw new ResponseException("Error in updating details vehicle segment");
        return getVehicleSegmentDto(vehicleSegmentDao);
    }

    @Override
    public VehicleSegmentDto getSegmentDetails(String segmentUUID) {
        VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findByUUID(segmentUUID);
        if (vehicleSegmentDao == null)
            throw new ResponseException("No vehicle segment found for Id :" + segmentUUID);
        return getVehicleSegmentDto(vehicleSegmentDao);
    }

    @Override
    public Boolean deleteSegment(String segmentUUID) {
        VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findByUUID(segmentUUID);
        if (vehicleSegmentDao == null)
            throw new ResponseException("No vehicle segment found for Id :" + segmentUUID);
        vehicleSegmentRepository.delete(vehicleSegmentDao.getId());
        return true;
    }
}
