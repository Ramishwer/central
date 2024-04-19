package com.goev.central.service.vehicle.impl;

import com.goev.central.dao.vehicle.detail.VehicleManufacturerDao;
import com.goev.central.dao.vehicle.detail.VehicleModelDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.detail.VehicleManufacturerDto;
import com.goev.central.dto.vehicle.detail.VehicleModelDto;
import com.goev.central.repository.vehicle.detail.VehicleManufacturerRepository;
import com.goev.central.repository.vehicle.detail.VehicleModelRepository;
import com.goev.central.service.vehicle.VehicleModelService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleModelServiceImpl implements VehicleModelService {

    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleManufacturerRepository vehicleManufacturerRepository;

    @Override
    public PaginatedResponseDto<VehicleModelDto> getModels() {
        PaginatedResponseDto<VehicleModelDto> result = PaginatedResponseDto.<VehicleModelDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleModelDao> vehicleModelDaos = vehicleModelRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleModelDaos))
            return result;

        List<VehicleManufacturerDao> vehicleManufacturerDaosForVehicleModels = vehicleManufacturerRepository.findAllByIds(vehicleModelDaos.stream().map(VehicleModelDao::getManufacturerId).collect(Collectors.toList()));
        Map<Integer, VehicleManufacturerDao> vehicleManufacturerMap = vehicleManufacturerDaosForVehicleModels.stream().collect(Collectors.toMap(VehicleManufacturerDao::getId, Function.identity()));

        for (VehicleModelDao vehicleModelDao : vehicleModelDaos) {
            VehicleManufacturerDao manufacturerDao = vehicleManufacturerMap.get(vehicleModelDao.getManufacturerId());
            result.getElements().add(VehicleModelDto.builder()
                    .year(vehicleModelDao.getYear())
                    .name(vehicleModelDao.getName())
                    .kmRange(vehicleModelDao.getKmRange())
                    .variant(vehicleModelDao.getVariant())
                    .month(vehicleModelDao.getMonth())
                    .uuid(vehicleModelDao.getUuid())
                    .batteryCapacity(vehicleModelDao.getBatteryCapacity())
                    .manufacturer(VehicleManufacturerDto.builder()
                            .name(manufacturerDao.getName())
                            .uuid(manufacturerDao.getUuid())
                            .build())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleModelDto createModel(VehicleModelDto vehicleModelDto) {
        VehicleModelDao vehicleModelDao = new VehicleModelDao();

        vehicleModelDao.setName(vehicleModelDto.getName());
        vehicleModelDao.setVariant(vehicleModelDto.getVariant());
        vehicleModelDao.setMonth(vehicleModelDto.getMonth());
        vehicleModelDao.setYear(vehicleModelDto.getYear());
        vehicleModelDao.setBatteryCapacity(vehicleModelDto.getBatteryCapacity());
        vehicleModelDao.setKmRange(vehicleModelDto.getKmRange());

        if (vehicleModelDto.getManufacturer() == null || vehicleModelDto.getManufacturer().getUuid() == null)
            throw new ResponseException("Error in saving vehicle model: Invalid Manufacturer");
        VehicleManufacturerDao manufacturerDao = vehicleManufacturerRepository.findByUUID(vehicleModelDto.getManufacturer().getUuid());

        if (manufacturerDao == null || manufacturerDao.getId() == null)
            throw new ResponseException("Error in saving vehicle model: Invalid Manufacturer");

        vehicleModelDao.setManufacturerId(manufacturerDao.getId());
        vehicleModelDao = vehicleModelRepository.save(vehicleModelDao);
        if (vehicleModelDao == null)
            throw new ResponseException("Error in saving vehicle model");

        return VehicleModelDto.builder()
                .year(vehicleModelDao.getYear())
                .name(vehicleModelDao.getName())
                .kmRange(vehicleModelDao.getKmRange())
                .variant(vehicleModelDao.getVariant())
                .month(vehicleModelDao.getMonth())
                .uuid(vehicleModelDao.getUuid())
                .batteryCapacity(vehicleModelDao.getBatteryCapacity())
                .manufacturer(VehicleManufacturerDto.builder()
                        .name(manufacturerDao.getName())
                        .uuid(manufacturerDao.getUuid())
                        .build())
                .build();
    }

    @Override
    public VehicleModelDto updateModel(String modelUUID, VehicleModelDto vehicleModelDto) {

        VehicleModelDao vehicleModelDao = vehicleModelRepository.findByUUID(modelUUID);
        if (vehicleModelDao == null)
            throw new ResponseException("No vehicle model found for Id :" + modelUUID);


        if (vehicleModelDto.getManufacturer() == null || vehicleModelDto.getManufacturer().getUuid() == null)
            throw new ResponseException("Error in saving vehicle model: Invalid Manufacturer");
        VehicleManufacturerDao manufacturerDao = vehicleManufacturerRepository.findByUUID(vehicleModelDto.getManufacturer().getUuid());

        if (manufacturerDao == null || manufacturerDao.getId() == null)
            throw new ResponseException("Error in saving vehicle model: Invalid Manufacturer");


        VehicleModelDao newVehicleModelDao = new VehicleModelDao();
        newVehicleModelDao.setManufacturerId(manufacturerDao.getId());
        newVehicleModelDao.setName(vehicleModelDto.getName());
        newVehicleModelDao.setVariant(vehicleModelDto.getVariant());
        newVehicleModelDao.setBatteryCapacity(vehicleModelDto.getBatteryCapacity());
        newVehicleModelDao.setKmRange(vehicleModelDto.getKmRange());
        newVehicleModelDao.setMonth(vehicleModelDto.getMonth());
        newVehicleModelDao.setYear(vehicleModelDto.getYear());

        newVehicleModelDao.setId(vehicleModelDao.getId());
        newVehicleModelDao.setUuid(vehicleModelDao.getUuid());
        vehicleModelDao = vehicleModelRepository.update(newVehicleModelDao);
        if (vehicleModelDao == null)
            throw new ResponseException("Error in updating details vehicle manufacturer");
        return VehicleModelDto.builder()
                .year(vehicleModelDao.getYear())
                .name(vehicleModelDao.getName())
                .kmRange(vehicleModelDao.getKmRange())
                .variant(vehicleModelDao.getVariant())
                .month(vehicleModelDao.getMonth())
                .uuid(vehicleModelDao.getUuid())
                .batteryCapacity(vehicleModelDao.getBatteryCapacity())
                .manufacturer(VehicleManufacturerDto.builder()
                        .name(manufacturerDao.getName())
                        .uuid(manufacturerDao.getUuid())
                        .build())
                .build();
    }

    @Override
    public VehicleModelDto getModelDetails(String modelUUID) {
        VehicleModelDao vehicleModelDao = vehicleModelRepository.findByUUID(modelUUID);
        if (vehicleModelDao == null)
            throw new ResponseException("No vehicle model found for Id :" + modelUUID);

        VehicleManufacturerDao manufacturerDao = vehicleManufacturerRepository.findById(vehicleModelDao.getManufacturerId());


        return VehicleModelDto.builder()
                .year(vehicleModelDao.getYear())
                .name(vehicleModelDao.getName())
                .kmRange(vehicleModelDao.getKmRange())
                .variant(vehicleModelDao.getVariant())
                .month(vehicleModelDao.getMonth())
                .uuid(vehicleModelDao.getUuid())
                .batteryCapacity(vehicleModelDao.getBatteryCapacity())
                .manufacturer(VehicleManufacturerDto.builder()
                        .name(manufacturerDao.getName())
                        .uuid(manufacturerDao.getUuid())
                        .build())
                .build();
    }

    @Override
    public Boolean deleteModel(String modelUUID) {
        VehicleModelDao vehicleModelDao = vehicleModelRepository.findByUUID(modelUUID);
        if (vehicleModelDao == null)
            throw new ResponseException("No vehicle model found for Id :" + modelUUID);

        vehicleModelRepository.delete(vehicleModelDao.getId());
        return true;
    }
}
