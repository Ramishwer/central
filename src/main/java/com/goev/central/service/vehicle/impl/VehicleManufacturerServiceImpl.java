package com.goev.central.service.vehicle.impl;

import com.goev.central.dao.vehicle.detail.VehicleManufacturerDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleManufacturerDto;
import com.goev.central.repository.vehicle.detail.VehicleManufacturerRepository;
import com.goev.central.service.vehicle.VehicleManufacturerService;
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
public class VehicleManufacturerServiceImpl implements VehicleManufacturerService {

    private final VehicleManufacturerRepository vehicleManufacturerRepository;

    @Override
    public PaginatedResponseDto<VehicleManufacturerDto> getManufacturers() {
        PaginatedResponseDto<VehicleManufacturerDto> result = PaginatedResponseDto.<VehicleManufacturerDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleManufacturerDao> vehicleManufacturerDaos = vehicleManufacturerRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleManufacturerDaos))
            return result;

        for (VehicleManufacturerDao vehicleManufacturerDao : vehicleManufacturerDaos) {
            result.getElements().add(VehicleManufacturerDto.builder()
                    .name(vehicleManufacturerDao.getName())
                    .uuid(vehicleManufacturerDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleManufacturerDto createManufacturer(VehicleManufacturerDto vehicleManufacturerDto) {

        VehicleManufacturerDao vehicleManufacturerDao = new VehicleManufacturerDao();
        vehicleManufacturerDao.setName(vehicleManufacturerDto.getName());
        vehicleManufacturerDao = vehicleManufacturerRepository.save(vehicleManufacturerDao);
        if (vehicleManufacturerDao == null)
            throw new ResponseException("Error in saving vehicle manufacturer");
        return VehicleManufacturerDto.builder().name(vehicleManufacturerDao.getName()).uuid(vehicleManufacturerDao.getUuid()).build();
    }

    @Override
    public VehicleManufacturerDto updateManufacturer(String manufacturerUUID, VehicleManufacturerDto vehicleManufacturerDto) {
        VehicleManufacturerDao vehicleManufacturerDao = vehicleManufacturerRepository.findByUUID(manufacturerUUID);
        if (vehicleManufacturerDao == null)
            throw new ResponseException("No vehicle manufacturer found for Id :" + manufacturerUUID);

        VehicleManufacturerDao newVehicleManufacturerDao = new VehicleManufacturerDao();
        newVehicleManufacturerDao.setName(vehicleManufacturerDto.getName());

        newVehicleManufacturerDao.setId(vehicleManufacturerDao.getId());
        newVehicleManufacturerDao.setUuid(vehicleManufacturerDao.getUuid());
        vehicleManufacturerDao = vehicleManufacturerRepository.update(newVehicleManufacturerDao);
        if (vehicleManufacturerDao == null)
            throw new ResponseException("Error in updating details vehicle manufacturer");
        return VehicleManufacturerDto.builder()
                .name(vehicleManufacturerDao.getName())
                .uuid(vehicleManufacturerDao.getUuid()).build();
    }

    @Override
    public VehicleManufacturerDto getManufacturerDetails(String manufacturerUUID) {
        VehicleManufacturerDao vehicleManufacturerDao = vehicleManufacturerRepository.findByUUID(manufacturerUUID);
        if (vehicleManufacturerDao == null)
            throw new ResponseException("No vehicle manufacturer found for Id :" + manufacturerUUID);
        return VehicleManufacturerDto.builder()
                .name(vehicleManufacturerDao.getName())
                .uuid(vehicleManufacturerDao.getUuid()).build();
    }

    @Override
    public Boolean deleteManufacturer(String manufacturerUUID) {
        VehicleManufacturerDao vehicleManufacturerDao = vehicleManufacturerRepository.findByUUID(manufacturerUUID);
        if (vehicleManufacturerDao == null)
            throw new ResponseException("No vehicle manufacturer found for Id :" + manufacturerUUID);
        vehicleManufacturerRepository.delete(vehicleManufacturerDao.getId());
        return true;
    }
}
