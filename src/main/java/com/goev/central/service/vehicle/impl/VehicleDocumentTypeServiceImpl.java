package com.goev.central.service.vehicle.impl;

import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.document.VehicleDocumentTypeDto;
import com.goev.central.repository.vehicle.document.VehicleDocumentTypeRepository;
import com.goev.central.service.vehicle.VehicleDocumentTypeService;
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
public class VehicleDocumentTypeServiceImpl implements VehicleDocumentTypeService {
   
    private final VehicleDocumentTypeRepository vehicleDocumentTypeRepository;

    @Override
    public PaginatedResponseDto<VehicleDocumentTypeDto> getDocumentTypes() {
        PaginatedResponseDto<VehicleDocumentTypeDto> result = PaginatedResponseDto.<VehicleDocumentTypeDto>builder().totalPages(0).currentPage(0).elements(new ArrayList<>()).build();
        List<VehicleDocumentTypeDao> vehicleDocumentTypeDaos = vehicleDocumentTypeRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleDocumentTypeDaos))
            return result;

        for (VehicleDocumentTypeDao vehicleDocumentTypeDao : vehicleDocumentTypeDaos) {
            result.getElements().add(VehicleDocumentTypeDto.builder()
                    .name(vehicleDocumentTypeDao.getName())
                    .uuid(vehicleDocumentTypeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleDocumentTypeDto createDocumentType(VehicleDocumentTypeDto vehicleDocumentTypeDto) {

        VehicleDocumentTypeDao vehicleDocumentTypeDao = new VehicleDocumentTypeDao();
        vehicleDocumentTypeDao.setName(vehicleDocumentTypeDto.getName());
        vehicleDocumentTypeDao = vehicleDocumentTypeRepository.save(vehicleDocumentTypeDao);
        if (vehicleDocumentTypeDao == null)
            throw new ResponseException("Error in saving vehicle documentType");
        return VehicleDocumentTypeDto.builder().name(vehicleDocumentTypeDao.getName()).uuid(vehicleDocumentTypeDao.getUuid()).build();
    }

    @Override
    public VehicleDocumentTypeDto updateDocumentType(String documentTypeUUID, VehicleDocumentTypeDto vehicleDocumentTypeDto) {
        VehicleDocumentTypeDao vehicleDocumentTypeDao = vehicleDocumentTypeRepository.findByUUID(documentTypeUUID);
        if (vehicleDocumentTypeDao == null)
            throw new ResponseException("No vehicle documentType found for Id :" + documentTypeUUID);
        VehicleDocumentTypeDao newVehicleDocumentTypeDao = new VehicleDocumentTypeDao();
        newVehicleDocumentTypeDao.setName(vehicleDocumentTypeDto.getName());

        newVehicleDocumentTypeDao.setId(vehicleDocumentTypeDao.getId());
        newVehicleDocumentTypeDao.setUuid(vehicleDocumentTypeDao.getUuid());
        vehicleDocumentTypeDao = vehicleDocumentTypeRepository.update(newVehicleDocumentTypeDao);
        if (vehicleDocumentTypeDao == null)
            throw new ResponseException("Error in updating details vehicle documentType");
        return VehicleDocumentTypeDto.builder()
                .name(vehicleDocumentTypeDao.getName())
                .uuid(vehicleDocumentTypeDao.getUuid()).build();
    }

    @Override
    public VehicleDocumentTypeDto getDocumentTypeDetails(String documentTypeUUID) {
        VehicleDocumentTypeDao vehicleDocumentTypeDao = vehicleDocumentTypeRepository.findByUUID(documentTypeUUID);
        if (vehicleDocumentTypeDao == null)
            throw new ResponseException("No vehicle documentType found for Id :" + documentTypeUUID);
        return VehicleDocumentTypeDto.builder()
                .name(vehicleDocumentTypeDao.getName())
                .uuid(vehicleDocumentTypeDao.getUuid()).build();
    }

    @Override
    public Boolean deleteDocumentType(String documentTypeUUID) {
        VehicleDocumentTypeDao vehicleDocumentTypeDao = vehicleDocumentTypeRepository.findByUUID(documentTypeUUID);
        if (vehicleDocumentTypeDao == null)
            throw new ResponseException("No vehicle documentType found for Id :" + documentTypeUUID);
        vehicleDocumentTypeRepository.delete(vehicleDocumentTypeDao.getId());
        return true;
    }
}
