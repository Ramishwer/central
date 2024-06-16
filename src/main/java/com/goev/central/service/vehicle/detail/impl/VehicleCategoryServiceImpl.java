package com.goev.central.service.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleCategoryDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleCategoryDto;
import com.goev.central.repository.vehicle.detail.VehicleCategoryRepository;
import com.goev.central.service.vehicle.detail.VehicleCategoryService;
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
public class VehicleCategoryServiceImpl implements VehicleCategoryService {

    private final VehicleCategoryRepository vehicleCategoryRepository;

    @Override
    public PaginatedResponseDto<VehicleCategoryDto> getCategories() {
        PaginatedResponseDto<VehicleCategoryDto> result = PaginatedResponseDto.<VehicleCategoryDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleCategoryDao> vehicleCategoryDaos = vehicleCategoryRepository.findAllActive();
        if (CollectionUtils.isEmpty(vehicleCategoryDaos))
            return result;

        for (VehicleCategoryDao vehicleCategoryDao : vehicleCategoryDaos) {
            result.getElements().add(getVehicleCategoryDto(vehicleCategoryDao));
        }
        return result;
    }

    private VehicleCategoryDto getVehicleCategoryDto(VehicleCategoryDao vehicleCategoryDao) {
        return VehicleCategoryDto.builder()
                .name(vehicleCategoryDao.getName())
                .description(vehicleCategoryDao.getDescription())
                .uuid(vehicleCategoryDao.getUuid())
                .build();
    }

    @Override
    public VehicleCategoryDto createCategory(VehicleCategoryDto vehicleCategoryDto) {

        VehicleCategoryDao vehicleCategoryDao = getVehicleCategoryDao(vehicleCategoryDto);
        vehicleCategoryDao = vehicleCategoryRepository.save(vehicleCategoryDao);
        if (vehicleCategoryDao == null)
            throw new ResponseException("Error in saving vehicle category");
        return getVehicleCategoryDto(vehicleCategoryDao);
    }

    private VehicleCategoryDao getVehicleCategoryDao(VehicleCategoryDto vehicleCategoryDto) {
        VehicleCategoryDao vehicleCategoryDao = new VehicleCategoryDao();
        vehicleCategoryDao.setName(vehicleCategoryDto.getName());
        vehicleCategoryDao.setDescription(vehicleCategoryDto.getDescription());
        return vehicleCategoryDao;
    }

    @Override
    public VehicleCategoryDto updateCategory(String categoryUUID, VehicleCategoryDto vehicleCategoryDto) {
        VehicleCategoryDao vehicleCategoryDao = vehicleCategoryRepository.findByUUID(categoryUUID);
        if (vehicleCategoryDao == null)
            throw new ResponseException("No vehicle category found for Id :" + categoryUUID);
        VehicleCategoryDao newVehicleCategoryDao = getVehicleCategoryDao(vehicleCategoryDto);

        newVehicleCategoryDao.setId(vehicleCategoryDao.getId());
        newVehicleCategoryDao.setUuid(vehicleCategoryDao.getUuid());
        vehicleCategoryDao = vehicleCategoryRepository.update(newVehicleCategoryDao);
        if (vehicleCategoryDao == null)
            throw new ResponseException("Error in updating details vehicle category");
        return getVehicleCategoryDto(vehicleCategoryDao);
    }

    @Override
    public VehicleCategoryDto getCategoryDetails(String categoryUUID) {
        VehicleCategoryDao vehicleCategoryDao = vehicleCategoryRepository.findByUUID(categoryUUID);
        if (vehicleCategoryDao == null)
            throw new ResponseException("No vehicle category found for Id :" + categoryUUID);
        return getVehicleCategoryDto(vehicleCategoryDao);
    }

    @Override
    public Boolean deleteCategory(String categoryUUID) {
        VehicleCategoryDao vehicleCategoryDao = vehicleCategoryRepository.findByUUID(categoryUUID);
        if (vehicleCategoryDao == null)
            throw new ResponseException("No vehicle category found for Id :" + categoryUUID);
        vehicleCategoryRepository.delete(vehicleCategoryDao.getId());
        return true;
    }
}
