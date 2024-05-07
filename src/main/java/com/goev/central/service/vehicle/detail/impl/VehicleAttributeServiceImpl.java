package com.goev.central.service.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleAttributeDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleAttributeDto;
import com.goev.central.repository.vehicle.detail.VehicleAttributeRepository;
import com.goev.central.service.vehicle.detail.VehicleAttributeService;
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
public class VehicleAttributeServiceImpl implements VehicleAttributeService {

    private final VehicleAttributeRepository vehicleAttributeRepository;

    @Override
    public PaginatedResponseDto<VehicleAttributeDto> getVehicleAttributes(String vehicleUUID) {
        PaginatedResponseDto<VehicleAttributeDto> result = PaginatedResponseDto.<VehicleAttributeDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleAttributeDao> vehicleAttributeDaos = vehicleAttributeRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleAttributeDaos))
            return result;

        for (VehicleAttributeDao vehicleAttributeDao : vehicleAttributeDaos) {
            result.getElements().add(VehicleAttributeDto.builder()
                    .uuid(vehicleAttributeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleAttributeDto createVehicleAttribute(String vehicleUUID, VehicleAttributeDto vehicleAttributeDto) {

        VehicleAttributeDao vehicleAttributeDao = new VehicleAttributeDao();
        vehicleAttributeDao = vehicleAttributeRepository.save(vehicleAttributeDao);
        if (vehicleAttributeDao == null)
            throw new ResponseException("Error in saving vehicleAttribute vehicleAttribute");
        return VehicleAttributeDto.builder()
                .uuid(vehicleAttributeDao.getUuid()).build();
    }

    @Override
    public VehicleAttributeDto updateVehicleAttribute(String vehicleUUID, String vehicleAttributeUUID, VehicleAttributeDto vehicleAttributeDto) {
        VehicleAttributeDao vehicleAttributeDao = vehicleAttributeRepository.findByUUID(vehicleAttributeUUID);
        if (vehicleAttributeDao == null)
            throw new ResponseException("No vehicleAttribute  found for Id :" + vehicleAttributeUUID);
        VehicleAttributeDao newVehicleAttributeDao = new VehicleAttributeDao();
       

        newVehicleAttributeDao.setId(vehicleAttributeDao.getId());
        newVehicleAttributeDao.setUuid(vehicleAttributeDao.getUuid());
        vehicleAttributeDao = vehicleAttributeRepository.update(newVehicleAttributeDao);
        if (vehicleAttributeDao == null)
            throw new ResponseException("Error in updating details vehicleAttribute ");
        return VehicleAttributeDto.builder()
                .uuid(vehicleAttributeDao.getUuid()).build();
    }

    @Override
    public VehicleAttributeDto getVehicleAttributeDetails(String vehicleUUID, String vehicleAttributeUUID) {
        VehicleAttributeDao vehicleAttributeDao = vehicleAttributeRepository.findByUUID(vehicleAttributeUUID);
        if (vehicleAttributeDao == null)
            throw new ResponseException("No vehicleAttribute  found for Id :" + vehicleAttributeUUID);
        return VehicleAttributeDto.builder()
                .uuid(vehicleAttributeDao.getUuid()).build();
    }

    @Override
    public Boolean deleteVehicleAttribute(String vehicleUUID, String vehicleAttributeUUID) {
        VehicleAttributeDao vehicleAttributeDao = vehicleAttributeRepository.findByUUID(vehicleAttributeUUID);
        if (vehicleAttributeDao == null)
            throw new ResponseException("No vehicleAttribute  found for Id :" + vehicleAttributeUUID);
        vehicleAttributeRepository.delete(vehicleAttributeDao.getId());
        return true;
    }
}
