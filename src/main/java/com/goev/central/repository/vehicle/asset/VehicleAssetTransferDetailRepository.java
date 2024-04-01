package com.goev.central.repository.vehicle.asset;

import com.goev.central.dao.vehicle.asset.VehicleAssetTransferDetailDao;

import java.util.List;

public interface VehicleAssetTransferDetailRepository {
    VehicleAssetTransferDetailDao save(VehicleAssetTransferDetailDao partner);
    VehicleAssetTransferDetailDao update(VehicleAssetTransferDetailDao partner);
    void delete(Integer id);
    VehicleAssetTransferDetailDao findByUUID(String uuid);
    VehicleAssetTransferDetailDao findById(Integer id);
    List<VehicleAssetTransferDetailDao> findAllByIds(List<Integer> ids);
    List<VehicleAssetTransferDetailDao> findAll();
}