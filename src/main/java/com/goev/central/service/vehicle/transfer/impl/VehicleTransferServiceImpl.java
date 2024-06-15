package com.goev.central.service.vehicle.transfer.impl;

import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.transfer.VehicleTransferDetailDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.transfer.VehicleTransferDto;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.repository.vehicle.transfer.VehicleTransferDetailRepository;
import com.goev.central.service.vehicle.transfer.VehicleTransferService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class VehicleTransferServiceImpl implements VehicleTransferService {
    private final VehicleTransferDetailRepository vehicleTransferDetailRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public PaginatedResponseDto<VehicleTransferDto> getTransfersForVehicle(String vehicleUUID) {
        VehicleDao vehicleDao = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicleDao == null)
            throw new ResponseException("No vehicle  found for Id :" + vehicleUUID);

        PaginatedResponseDto<VehicleTransferDto> result = PaginatedResponseDto.<VehicleTransferDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleTransferDetailDao> vehicleTransferDaos = vehicleTransferDetailRepository.findAllByVehicleId(vehicleDao.getId());
        if (CollectionUtils.isEmpty(vehicleTransferDaos))
            return result;

        for (VehicleTransferDetailDao vehicleTransferDao : vehicleTransferDaos) {
            result.getElements().add(VehicleTransferDto.builder()
                    .uuid(vehicleTransferDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PaginatedResponseDto<VehicleTransferDto> getTransfers() {
        PaginatedResponseDto<VehicleTransferDto> result = PaginatedResponseDto.<VehicleTransferDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        return result;
    }

}
