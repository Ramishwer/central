package com.goev.central.repository.location;

import com.goev.central.dao.location.LocationAssetMappingDao;

import java.util.List;

public interface LocationAssetMappingRepository {
    LocationAssetMappingDao save(LocationAssetMappingDao location);

    LocationAssetMappingDao update(LocationAssetMappingDao location);

    void delete(Integer id);

    LocationAssetMappingDao findByUUID(String uuid);

    LocationAssetMappingDao findById(Integer id);

    List<LocationAssetMappingDao> findAllByIds(List<Integer> ids);

    List<LocationAssetMappingDao> findAllActive();

    List<LocationAssetMappingDao> findAllByLocationId(Integer id);

    LocationAssetMappingDao findByLocationIdAndAssetId(Integer locationId, Integer assetId);

    LocationAssetMappingDao findByAssetId(Integer id);
}