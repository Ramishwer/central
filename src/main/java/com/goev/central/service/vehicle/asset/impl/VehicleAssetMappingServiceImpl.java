package com.goev.central.service.vehicle.asset.impl;


import com.goev.central.dao.asset.AssetDao;
import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dao.vehicle.asset.VehicleAssetMappingDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.asset.AssetRepository;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.central.repository.vehicle.asset.VehicleAssetMappingRepository;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.service.vehicle.asset.VehicleAssetMappingService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleAssetMappingServiceImpl implements VehicleAssetMappingService {


    private final VehicleRepository vehicleRepository;
    private final VehicleAssetMappingRepository vehicleAssetMappingRepository;
    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;


    @Override
    public PaginatedResponseDto<AssetDto> getAssetsForVehicles(String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);
        List<VehicleAssetMappingDao> mappings = vehicleAssetMappingRepository.findAllByVehicleId(vehicle.getId());

        if (CollectionUtils.isEmpty(mappings))
            return PaginatedResponseDto.<AssetDto>builder().elements(new ArrayList<>()).build();

        List<AssetDao> assets = assetRepository.findAllByIds(mappings.stream().map(VehicleAssetMappingDao::getAssetId).collect(Collectors.toList()));

        return PaginatedResponseDto.<AssetDto>builder().elements(assets.stream().map(x->{
                AssetTypeDao type = assetTypeRepository.findById(x.getAssetTypeId());
        return AssetDto.fromDao(x, AssetTypeDto.fromDao(type));
        }).collect(Collectors.toList())).build();
    }

    @Override
    public AssetDto createAssetsForVehicle(AssetDto assetDto, String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        if (assetDto == null || assetDto.getUuid() == null)
            throw new ResponseException("No asset data  found");
        AssetDao assetDao = assetRepository.findByUUID(assetDto.getUuid());
        if (assetDao == null)
            throw new ResponseException("No asset found for Id :" + assetDto.getUuid());
        VehicleAssetMappingDao mapping = new VehicleAssetMappingDao();
        mapping.setVehicleId(vehicle.getId());
        mapping.setAssetId(assetDao.getId());
        vehicleAssetMappingRepository.save(mapping);
        return assetDto;
    }

    @Override
    public List<AssetDto> createBulkAssetsForVehicle(List<AssetDto> assetDtoList, String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        if (CollectionUtils.isEmpty(assetDtoList))
            return new ArrayList<>();

        for (AssetDto assetDto : assetDtoList) {
            AssetDao assetDao = assetRepository.findByUUID(assetDto.getUuid());
            if (assetDao == null)
                throw new ResponseException("No asset found for Id :" + assetDto.getUuid());
            VehicleAssetMappingDao mapping = new VehicleAssetMappingDao();
            mapping.setVehicleId(vehicle.getId());
            mapping.setAssetId(assetDao.getId());
            vehicleAssetMappingRepository.save(mapping);
        }

        return assetDtoList;
    }

    @Override
    public Boolean deleteAssetsForVehicle(String assetUUID, String vehicleUUID) {
        VehicleDao vehicle = vehicleRepository.findByUUID(vehicleUUID);
        if (vehicle == null)
            throw new ResponseException("No vehicle found for Id :" + vehicleUUID);

        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset found for Id :" + assetUUID);
        VehicleAssetMappingDao mapping = vehicleAssetMappingRepository.findByVehicleIdAndAssetId(vehicle.getId(), assetDao.getId());
        if (mapping == null)
            throw new ResponseException("No mapping found for vehicle Id:" + vehicleUUID + "and asset Id :" + assetUUID);

        vehicleAssetMappingRepository.delete(mapping.getId());
        return true;
    }

}
