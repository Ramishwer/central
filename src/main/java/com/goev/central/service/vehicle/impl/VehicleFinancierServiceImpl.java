package com.goev.central.service.vehicle.impl;

import com.goev.central.dao.vehicle.detail.VehicleFinancierDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleFinancierDto;
import com.goev.central.repository.vehicle.detail.VehicleFinancierRepository;
import com.goev.central.service.vehicle.VehicleFinancierService;
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
public class VehicleFinancierServiceImpl implements VehicleFinancierService {

    private final VehicleFinancierRepository vehicleFinancierRepository;

    @Override
    public PaginatedResponseDto<VehicleFinancierDto> getFinanciers() {
        PaginatedResponseDto<VehicleFinancierDto> result = PaginatedResponseDto.<VehicleFinancierDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<VehicleFinancierDao> vehicleFinancierDaos = vehicleFinancierRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleFinancierDaos))
            return result;

        for (VehicleFinancierDao vehicleFinancierDao : vehicleFinancierDaos) {
            result.getElements().add(VehicleFinancierDto.builder()
                    .name(vehicleFinancierDao.getName())
                    .uuid(vehicleFinancierDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleFinancierDto createFinancier(VehicleFinancierDto vehicleFinancierDto) {

        VehicleFinancierDao vehicleFinancierDao = new VehicleFinancierDao();
        vehicleFinancierDao.setName(vehicleFinancierDto.getName());
        vehicleFinancierDao = vehicleFinancierRepository.save(vehicleFinancierDao);
        if (vehicleFinancierDao == null)
            throw new ResponseException("Error in saving vehicle financier");
        return VehicleFinancierDto.builder().name(vehicleFinancierDao.getName()).uuid(vehicleFinancierDao.getUuid()).build();
    }

    @Override
    public VehicleFinancierDto updateFinancier(String financierUUID, VehicleFinancierDto vehicleFinancierDto) {
        VehicleFinancierDao vehicleFinancierDao = vehicleFinancierRepository.findByUUID(financierUUID);
        if (vehicleFinancierDao == null)
            throw new ResponseException("No vehicle financier found for Id :" + financierUUID);
        VehicleFinancierDao newVehicleFinancierDao = new VehicleFinancierDao();
        newVehicleFinancierDao.setName(vehicleFinancierDto.getName());

        newVehicleFinancierDao.setId(vehicleFinancierDao.getId());
        newVehicleFinancierDao.setUuid(vehicleFinancierDao.getUuid());
        vehicleFinancierDao = vehicleFinancierRepository.update(newVehicleFinancierDao);
        if (vehicleFinancierDao == null)
            throw new ResponseException("Error in updating details vehicle financier");
        return VehicleFinancierDto.builder()
                .name(vehicleFinancierDao.getName())
                .uuid(vehicleFinancierDao.getUuid()).build();
    }

    @Override
    public VehicleFinancierDto getFinancierDetails(String financierUUID) {
        VehicleFinancierDao vehicleFinancierDao = vehicleFinancierRepository.findByUUID(financierUUID);
        if (vehicleFinancierDao == null)
            throw new ResponseException("No vehicle financier found for Id :" + financierUUID);
        return VehicleFinancierDto.builder()
                .name(vehicleFinancierDao.getName())
                .uuid(vehicleFinancierDao.getUuid()).build();
    }

    @Override
    public Boolean deleteFinancier(String financierUUID) {
        VehicleFinancierDao vehicleFinancierDao = vehicleFinancierRepository.findByUUID(financierUUID);
        if (vehicleFinancierDao == null)
            throw new ResponseException("No vehicle financier found for Id :" + financierUUID);
        vehicleFinancierRepository.delete(vehicleFinancierDao.getId());
        return true;
    }
}
