package com.goev.central.repository.vehicle.asset;

import com.goev.central.dao.vehicle.asset.VehicleAssetsMappingDao;

import java.util.List;

public interface VehicleAssetsMappingRepository {
    VehicleAssetsMappingDao save(VehicleAssetsMappingDao partner);
    VehicleAssetsMappingDao update(VehicleAssetsMappingDao partner);
    void delete(Integer id);
    VehicleAssetsMappingDao findByUUID(String uuid);
    VehicleAssetsMappingDao findById(Integer id);
    List<VehicleAssetsMappingDao> findAllByIds(List<Integer> ids);
    List<VehicleAssetsMappingDao> findAll();
}