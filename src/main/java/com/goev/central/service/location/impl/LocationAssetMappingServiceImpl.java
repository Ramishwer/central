package com.goev.central.service.location.impl;


import com.goev.central.dao.asset.AssetDao;
import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.dao.location.LocationAssetMappingDao;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dto.asset.AssetDto;
import com.goev.central.dto.asset.AssetTypeDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.asset.AssetRepository;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.central.repository.location.LocationAssetMappingRepository;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.service.location.LocationAssetMappingService;
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
public class LocationAssetMappingServiceImpl implements LocationAssetMappingService {


    private final LocationRepository locationRepository;
    private final LocationAssetMappingRepository locationAssetMappingRepository;
    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;


    @Override
    public PaginatedResponseDto<AssetDto> getAssetsForLocations(String locationUUID) {
        LocationDao location = locationRepository.findByUUID(locationUUID);
        if (location == null)
            throw new ResponseException("No location found for Id :" + locationUUID);
        List<LocationAssetMappingDao> mappings = locationAssetMappingRepository.findAllByLocationId(location.getId());

        if (CollectionUtils.isEmpty(mappings))
            return PaginatedResponseDto.<AssetDto>builder().elements(new ArrayList<>()).build();

        List<AssetDao> assets = assetRepository.findAllByIds(mappings.stream().map(LocationAssetMappingDao::getAssetId).collect(Collectors.toList()));

        return PaginatedResponseDto.<AssetDto>builder().elements(assets.stream().map(x->{
            AssetTypeDao type = assetTypeRepository.findById(x.getAssetTypeId());
            return AssetDto.fromDao(x, AssetTypeDto.fromDao(type));
        }).collect(Collectors.toList())).build();
    }

    @Override
    public AssetDto createAssetsForLocation(AssetDto assetDto, String locationUUID) {
        LocationDao location = locationRepository.findByUUID(locationUUID);
        if (location == null)
            throw new ResponseException("No location found for Id :" + locationUUID);

        if (assetDto == null || assetDto.getUuid() == null)
            throw new ResponseException("No asset data  found");
        AssetDao assetDao = assetRepository.findByUUID(assetDto.getUuid());
        if (assetDao == null)
            throw new ResponseException("No asset found for Id :" + assetDto.getUuid());
        LocationAssetMappingDao mapping = new LocationAssetMappingDao();
        mapping.setLocationId(location.getId());
        mapping.setAssetId(assetDao.getId());
        locationAssetMappingRepository.save(mapping);
        return assetDto;
    }

    @Override
    public List<AssetDto> createBulkAssetsForLocation(List<AssetDto> assetDtoList, String locationUUID) {
        LocationDao location = locationRepository.findByUUID(locationUUID);
        if (location == null)
            throw new ResponseException("No location found for Id :" + locationUUID);

        if (CollectionUtils.isEmpty(assetDtoList))
            return new ArrayList<>();

        for (AssetDto assetDto : assetDtoList) {
            AssetDao assetDao = assetRepository.findByUUID(assetDto.getUuid());
            if (assetDao == null)
                throw new ResponseException("No asset found for Id :" + assetDto.getUuid());
            LocationAssetMappingDao mapping = new LocationAssetMappingDao();
            mapping.setLocationId(location.getId());
            mapping.setAssetId(assetDao.getId());
            locationAssetMappingRepository.save(mapping);
        }

        return assetDtoList;
    }

    @Override
    public Boolean deleteAssetsForLocation(String assetUUID, String locationUUID) {
        LocationDao location = locationRepository.findByUUID(locationUUID);
        if (location == null)
            throw new ResponseException("No location found for Id :" + locationUUID);

        AssetDao assetDao = assetRepository.findByUUID(assetUUID);
        if (assetDao == null)
            throw new ResponseException("No asset found for Id :" + assetUUID);
        LocationAssetMappingDao mapping = locationAssetMappingRepository.findByLocationIdAndAssetId(location.getId(), assetDao.getId());
        if (mapping == null)
            throw new ResponseException("No mapping found for location Id:" + locationUUID + "and asset Id :" + assetUUID);

        locationAssetMappingRepository.delete(mapping.getId());
        return true;
    }

}
