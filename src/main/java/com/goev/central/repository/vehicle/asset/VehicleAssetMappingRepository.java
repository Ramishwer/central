package com.goev.central.repository.vehicle.asset;

import com.goev.central.dao.vehicle.asset.VehicleAssetMappingDao;

import java.util.List;

public interface VehicleAssetMappingRepository {
    VehicleAssetMappingDao save(VehicleAssetMappingDao partner);

    VehicleAssetMappingDao update(VehicleAssetMappingDao partner);

    void delete(Integer id);

    VehicleAssetMappingDao findByUUID(String uuid);

    VehicleAssetMappingDao findById(Integer id);

    List<VehicleAssetMappingDao> findAllByIds(List<Integer> ids);

    List<VehicleAssetMappingDao> findAllActive();

    List<VehicleAssetMappingDao> findAllByVehicleId(Integer id);

    VehicleAssetMappingDao findByVehicleIdAndAssetId(Integer vehicleId, Integer assetId);

    VehicleAssetMappingDao findByAssetId(Integer id);
}