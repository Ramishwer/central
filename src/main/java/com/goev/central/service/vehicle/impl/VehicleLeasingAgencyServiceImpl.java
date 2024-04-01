package com.goev.central.service.vehicle.impl;

import com.goev.central.dao.vehicle.detail.VehicleLeasingAgencyDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleLeasingAgencyDto;
import com.goev.central.repository.vehicle.detail.VehicleLeasingAgencyRepository;
import com.goev.central.service.vehicle.VehicleLeasingAgencyService;
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
public class VehicleLeasingAgencyServiceImpl implements VehicleLeasingAgencyService {

    private final VehicleLeasingAgencyRepository vehicleLeasingAgencyRepository;

    @Override
    public PaginatedResponseDto<VehicleLeasingAgencyDto> getLeasingAgencies() {
        PaginatedResponseDto<VehicleLeasingAgencyDto> result = PaginatedResponseDto.<VehicleLeasingAgencyDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<VehicleLeasingAgencyDao> vehicleLeasingAgencyDaos = vehicleLeasingAgencyRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleLeasingAgencyDaos))
            return result;

        for (VehicleLeasingAgencyDao vehicleLeasingAgencyDao : vehicleLeasingAgencyDaos) {
            result.getElements().add(VehicleLeasingAgencyDto.builder()
                    .name(vehicleLeasingAgencyDao.getName())
                    .uuid(vehicleLeasingAgencyDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleLeasingAgencyDto createLeasingAgency(VehicleLeasingAgencyDto vehicleLeasingAgencyDto) {

        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = new VehicleLeasingAgencyDao();
        vehicleLeasingAgencyDao.setName(vehicleLeasingAgencyDto.getName());
        vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.save(vehicleLeasingAgencyDao);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("Error in saving vehicle leasing agency");
        return VehicleLeasingAgencyDto.builder().name(vehicleLeasingAgencyDao.getName()).uuid(vehicleLeasingAgencyDao.getUuid()).build();
    }

    @Override
    public VehicleLeasingAgencyDto updateLeasingAgency(String leasingAgencyUUID, VehicleLeasingAgencyDto vehicleLeasingAgencyDto) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findByUUID(leasingAgencyUUID);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("No vehicle leasingAgency found for Id :" + leasingAgencyUUID);

        VehicleLeasingAgencyDao newVehicleLeasingAgencyDao = new VehicleLeasingAgencyDao();
        newVehicleLeasingAgencyDao.setName(vehicleLeasingAgencyDto.getName());

        newVehicleLeasingAgencyDao.setId(vehicleLeasingAgencyDao.getId());
        newVehicleLeasingAgencyDao.setUuid(vehicleLeasingAgencyDao.getUuid());
        vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.update(newVehicleLeasingAgencyDao);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("Error in updating details vehicle leasingAgency");
        return VehicleLeasingAgencyDto.builder()
                .name(vehicleLeasingAgencyDao.getName())
                .uuid(vehicleLeasingAgencyDao.getUuid()).build();
    }

    @Override
    public VehicleLeasingAgencyDto getLeasingAgencyDetails(String leasingAgencyUUID) {
        VehicleLeasingAgencyDao vehicleLeasingAgencyDao = vehicleLeasingAgencyRepository.findByUUID(leasingAgencyUUID);
        if (vehicleLeasingAgencyDao == null)
            throw new ResponseException("No vehicle leasingAgency found for Id :" + leasingAgencyUUID);
        return VehicleLeasingAgencyDto.builder()
                .name(vehicleLeasingAgencyDao.getName())
                .uuid(vehicleLeasingAgencyDao.getUuid()).build();
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
