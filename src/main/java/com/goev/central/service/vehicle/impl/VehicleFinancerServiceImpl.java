package com.goev.central.service.vehicle.impl;

import com.goev.central.dao.vehicle.detail.VehicleFinancerDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleFinancerDto;
import com.goev.central.repository.vehicle.detail.VehicleFinancerRepository;
import com.goev.central.service.vehicle.VehicleFinancerService;
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
public class VehicleFinancerServiceImpl implements VehicleFinancerService {

    private final VehicleFinancerRepository vehicleFinancerRepository;

    @Override
    public PaginatedResponseDto<VehicleFinancerDto> getFinancers() {
        PaginatedResponseDto<VehicleFinancerDto> result = PaginatedResponseDto.<VehicleFinancerDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<VehicleFinancerDao> vehicleFinancerDaos = vehicleFinancerRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleFinancerDaos))
            return result;

        for (VehicleFinancerDao vehicleFinancerDao : vehicleFinancerDaos) {
            result.getElements().add(VehicleFinancerDto.builder()
                    .name(vehicleFinancerDao.getName())
                    .uuid(vehicleFinancerDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleFinancerDto createFinancer(VehicleFinancerDto vehicleFinancerDto) {

        VehicleFinancerDao vehicleFinancerDao = new VehicleFinancerDao();
        vehicleFinancerDao.setName(vehicleFinancerDto.getName());
        vehicleFinancerDao = vehicleFinancerRepository.save(vehicleFinancerDao);
        if (vehicleFinancerDao == null)
            throw new ResponseException("Error in saving vehicle financer");
        return VehicleFinancerDto.builder().name(vehicleFinancerDao.getName()).uuid(vehicleFinancerDao.getUuid()).build();
    }

    @Override
    public VehicleFinancerDto updateFinancer(String financerUUID, VehicleFinancerDto vehicleFinancerDto) {
        VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findByUUID(financerUUID);
        if (vehicleFinancerDao == null)
            throw new ResponseException("No vehicle financer found for Id :" + financerUUID);
        VehicleFinancerDao newVehicleFinancerDao = new VehicleFinancerDao();
        newVehicleFinancerDao.setName(vehicleFinancerDto.getName());

        newVehicleFinancerDao.setId(vehicleFinancerDao.getId());
        newVehicleFinancerDao.setUuid(vehicleFinancerDao.getUuid());
        vehicleFinancerDao = vehicleFinancerRepository.update(newVehicleFinancerDao);
        if (vehicleFinancerDao == null)
            throw new ResponseException("Error in updating details vehicle financer");
        return VehicleFinancerDto.builder()
                .name(vehicleFinancerDao.getName())
                .uuid(vehicleFinancerDao.getUuid()).build();
    }

    @Override
    public VehicleFinancerDto getFinancerDetails(String financerUUID) {
        VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findByUUID(financerUUID);
        if (vehicleFinancerDao == null)
            throw new ResponseException("No vehicle financer found for Id :" + financerUUID);
        return VehicleFinancerDto.builder()
                .name(vehicleFinancerDao.getName())
                .uuid(vehicleFinancerDao.getUuid()).build();
    }

    @Override
    public Boolean deleteFinancer(String financerUUID) {
        VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findByUUID(financerUUID);
        if (vehicleFinancerDao == null)
            throw new ResponseException("No vehicle financer found for Id :" + financerUUID);
        vehicleFinancerRepository.delete(vehicleFinancerDao.getId());
        return true;
    }
}
