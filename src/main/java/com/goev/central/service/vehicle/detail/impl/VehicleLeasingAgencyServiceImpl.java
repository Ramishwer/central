package com.goev.central.service.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleLeasingAgencyDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleLeasingAgencyDto;
import com.goev.central.repository.vehicle.detail.VehicleLeasingAgencyRepository;
import com.goev.central.service.vehicle.detail.VehicleLeasingAgencyService;
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
public class VehicleLeasingAgencyServiceImpl implements VehicleLeasingAgencyService {

    private final VehicleLeasingAgencyRepository vehicleLeasingAgencyRepository;

    @Override
    public PaginatedResponseDto<VehicleLeasingAgencyDto> getLeasingAgencies() {
        PaginatedResponseDto<VehicleLeasingAgencyDto> result = PaginatedResponseDto.<VehicleLeasingAgencyDto>builder().elements(new ArrayList<>()).build();
        List<VehicleLeasingAgencyDao> vehicleLeasingAgencyDaos = vehicleLeasingAgencyRepository.findAllActive();
        if (CollectionUtils.isEmpty(vehicleLeasingAgencyDaos))
            return result;

        for (VehicleLeasingAgencyDao vehicleLeasingAgencyDao : vehicleLeasingAgencyDaos) {
            result.getElements().add(getVehicleLeasingAgencyDto(vehicleLeasingAgencyDao));
        }
        return result;
    }

    private VehicleLeasingAgencyDto getVehicleLeasingAgencyDto(VehicleLeasingAgencyDao vehicleLeasingAgencyDao) {
        return VehicleLeasingAgencyDto.builder()
                .name(vehicleLeasingAgencyDao.getName())
                .uuid(vehicleLeasingAgencyDao.getUuid())
                .description(vehicleLeasingAgencyDao.getDescription())
                .build();
    }

    @Override
    public VehicleLeasingAgencyDto createLeasingAgency(VehicleLeasingAgencyDto vehicleLeasingAgencyDto) {

        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = getVehicleLeasingAgencyDao(vehicleLeasingAgencyDto);
        vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.save(vehicleLeasingAgencyDao);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("Error in saving vehicle leasing agency");
        return getVehicleLeasingAgencyDto(vehicleLeasingAgencyDao);
    }

    private VehicleLeasingAgencyDao getVehicleLeasingAgencyDao(VehicleLeasingAgencyDto vehicleLeasingAgencyDto) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = new VehicleLeasingAgencyDao();
        vehicleLeasingAgencyDao.setName(vehicleLeasingAgencyDto.getName());
        vehicleLeasingAgencyDao.setDescription(vehicleLeasingAgencyDto.getDescription());
        return vehicleLeasingAgencyDao;
    }

    @Override
    public VehicleLeasingAgencyDto updateLeasingAgency(String leasingAgencyUUID, VehicleLeasingAgencyDto vehicleLeasingAgencyDto) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findByUUID(leasingAgencyUUID);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("No vehicle leasingAgency found for Id :" + leasingAgencyUUID);

        VehicleLeasingAgencyDao newVehicleLeasingAgencyDao = getVehicleLeasingAgencyDao(vehicleLeasingAgencyDto);

        newVehicleLeasingAgencyDao.setId(vehicleLeasingAgencyDao.getId());
        newVehicleLeasingAgencyDao.setUuid(vehicleLeasingAgencyDao.getUuid());
        vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.update(newVehicleLeasingAgencyDao);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("Error in updating details vehicle leasingAgency");
        return getVehicleLeasingAgencyDto(vehicleLeasingAgencyDao);
    }

    @Override
    public VehicleLeasingAgencyDto getLeasingAgencyDetails(String leasingAgencyUUID) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findByUUID(leasingAgencyUUID);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("No vehicle leasingAgency found for Id :" + leasingAgencyUUID);
        return getVehicleLeasingAgencyDto(vehicleLeasingAgencyDao);
    }

    @Override
    public Boolean deleteLeasingAgency(String leasingAgencyUUID) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findByUUID(leasingAgencyUUID);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("No vehicle leasingAgency found for Id :" + leasingAgencyUUID);
        vehicleLeasingAgencyRepository.delete(vehicleLeasingAgencyDao.getId());
        return true;
    }
}
