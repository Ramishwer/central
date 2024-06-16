package com.goev.central.service.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleFinancerDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleFinancerDto;
import com.goev.central.repository.vehicle.detail.VehicleFinancerRepository;
import com.goev.central.service.vehicle.detail.VehicleFinancerService;
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

    private VehicleFinancerDao getVehicleFinancerDao(VehicleFinancerDto vehicleFinancerDto) {
        VehicleFinancerDao vehicleFinancerDao = new VehicleFinancerDao();
        vehicleFinancerDao.setName(vehicleFinancerDto.getName());
        vehicleFinancerDao.setDescription(vehicleFinancerDto.getDescription());
        return vehicleFinancerDao;
    }

    @Override
    public PaginatedResponseDto<VehicleFinancerDto> getFinancers() {
        PaginatedResponseDto<VehicleFinancerDto> result = PaginatedResponseDto.<VehicleFinancerDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleFinancerDao> vehicleFinancerDaos = vehicleFinancerRepository.findAllActive();
        if (CollectionUtils.isEmpty(vehicleFinancerDaos))
            return result;

        for (VehicleFinancerDao vehicleFinancerDao : vehicleFinancerDaos) {
            result.getElements().add(getVehicleFinancerDto(vehicleFinancerDao));
        }
        return result;
    }

    private VehicleFinancerDto getVehicleFinancerDto(VehicleFinancerDao vehicleFinancerDao) {
        return VehicleFinancerDto.builder()
                .name(vehicleFinancerDao.getName())
                .description(vehicleFinancerDao.getDescription())
                .uuid(vehicleFinancerDao.getUuid())
                .build();
    }

    @Override
    public VehicleFinancerDto createFinancer(VehicleFinancerDto vehicleFinancerDto) {

        VehicleFinancerDao vehicleFinancerDao = getVehicleFinancerDao(vehicleFinancerDto);
        vehicleFinancerDao = vehicleFinancerRepository.save(vehicleFinancerDao);
        if (vehicleFinancerDao == null)
            throw new ResponseException("Error in saving vehicle financer");
        return getVehicleFinancerDto(vehicleFinancerDao);
    }

    @Override
    public VehicleFinancerDto updateFinancer(String financerUUID, VehicleFinancerDto vehicleFinancerDto) {
        VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findByUUID(financerUUID);
        if (vehicleFinancerDao == null)
            throw new ResponseException("No vehicle financer found for Id :" + financerUUID);
        VehicleFinancerDao newVehicleFinancerDao = getVehicleFinancerDao(vehicleFinancerDto);

        newVehicleFinancerDao.setId(vehicleFinancerDao.getId());
        newVehicleFinancerDao.setUuid(vehicleFinancerDao.getUuid());
        vehicleFinancerDao = vehicleFinancerRepository.update(newVehicleFinancerDao);
        if (vehicleFinancerDao == null)
            throw new ResponseException("Error in updating details vehicle financer");
        return getVehicleFinancerDto(vehicleFinancerDao);
    }

    @Override
    public VehicleFinancerDto getFinancerDetails(String financerUUID) {
        VehicleFinancerDao vehicleFinancerDao = vehicleFinancerRepository.findByUUID(financerUUID);
        if (vehicleFinancerDao == null)
            throw new ResponseException("No vehicle financer found for Id :" + financerUUID);
        return getVehicleFinancerDto(vehicleFinancerDao);
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
