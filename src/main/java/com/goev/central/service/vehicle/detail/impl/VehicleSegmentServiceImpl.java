package com.goev.central.service.vehicle.detail.impl;

import com.goev.central.dao.partner.detail.PartnerSegmentMappingDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleSegmentDao;
import com.goev.central.dao.vehicle.detail.VehicleSegmentMappingDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentDto;
import com.goev.central.dto.vehicle.detail.VehicleSegmentMappingDto;
import com.goev.central.enums.vehicle.VehicleOnboardingStatus;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.repository.vehicle.detail.VehicleSegmentMappingRepository;
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
    private final VehicleRepository vehicleRepository;
    private final VehicleSegmentMappingRepository vehicleSegmentMappingRepository;

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


    @Override
    public VehicleSegmentMappingDto createVehicleMapping(String segmentUUID, VehicleSegmentMappingDto vehicleSegmentMappingDto) {
        VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findByUUID(segmentUUID);
        if (vehicleSegmentDao == null)
            throw new ResponseException("No vehicle segment found for Id :" + segmentUUID);

        if (vehicleSegmentMappingDto.getVehicle() == null)
            throw new ResponseException("No vehicle details present.");

        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleSegmentMappingDto.getVehicle().getUuid());

        if (vehicleDao == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleSegmentMappingDto.getVehicle().getUuid());

        List<VehicleSegmentMappingDao> mappings = vehicleSegmentMappingRepository.findAllByVehicleId(vehicleDao.getId());
        if(!CollectionUtils.isEmpty(mappings))
            throw new ResponseException("Only one mapping can be created for a vehicle :" + vehicleSegmentMappingDto.getVehicle().getUuid());

        VehicleSegmentMappingDao mappingDao = new VehicleSegmentMappingDao();

        mappingDao.setVehicleId(vehicleDao.getId());
        mappingDao.setVehicleSegmentId(vehicleSegmentDao.getId());
        mappingDao = vehicleSegmentMappingRepository.save(mappingDao);

        return VehicleSegmentMappingDto.fromDao(mappingDao, VehicleSegmentDto.fromDao(vehicleSegmentDao), VehicleViewDto.fromDao(vehicleDao));
    }

    @Override
    public Boolean deleteVehicleMapping(String segmentUUID, String vehicleSegmentMappingUUID) {
        VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findByUUID(segmentUUID);
        if (vehicleSegmentDao == null)
            throw new ResponseException("No vehicle segment found for Id :" + segmentUUID);

        VehicleSegmentMappingDao vehicleSegmentMappingDao = vehicleSegmentMappingRepository.findByUUID(vehicleSegmentMappingUUID);
        if (vehicleSegmentMappingDao == null)
            throw new ResponseException("No vehicle segment Mapping found for Id :" + vehicleSegmentMappingUUID);
        vehicleSegmentMappingRepository.delete(vehicleSegmentMappingDao.getId());
        return true;
    }

    @Override
    public List<VehicleSegmentMappingDto> getVehicleMappings(String segmentUUID) {
        VehicleSegmentDao vehicleSegmentDao = vehicleSegmentRepository.findByUUID(segmentUUID);
        if (vehicleSegmentDao == null)
            throw new ResponseException("No vehicle segment found for Id :" + segmentUUID);

        List<VehicleSegmentMappingDao> vehicleSegmentMappingDaoList = vehicleSegmentMappingRepository.findAllBySegmentId(vehicleSegmentDao.getId());

        List<VehicleSegmentMappingDto> result = new ArrayList<>();

        for (VehicleSegmentMappingDao vehicleSegmentMappingDao : vehicleSegmentMappingDaoList) {
            VehicleDao vehicle = vehicleRepository.findById(vehicleSegmentMappingDao.getVehicleId());
            if (vehicle == null || VehicleOnboardingStatus.DEBOARDED.name().equals(vehicle.getOnboardingStatus()))
                continue;
            result.add(VehicleSegmentMappingDto.fromDao(vehicleSegmentMappingDao, VehicleSegmentDto.fromDao(vehicleSegmentDao), VehicleViewDto.fromDao(vehicle)));
        }
        return result;
    }

    @Override
    public List<VehicleSegmentDto> getSegmentsForVehicle(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);
        List<VehicleSegmentDao> vehicleSegmentDaoList = vehicleSegmentRepository.findAllByVehicleId(vehicle.getId());
        List<VehicleSegmentDto> result = new ArrayList<>();

        for (VehicleSegmentDao vehicleSegmentDao : vehicleSegmentDaoList) {
            result.add(VehicleSegmentDto.fromDao(vehicleSegmentDao));
        }
        return result;
    }

}
