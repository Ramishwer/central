package com.goev.central.repository.vehicle.asset;

import com.goev.central.dao.vehicle.asset.VehicleTransferDetailDao;

import java.util.List;

public interface VehicleTransferDetailRepository {
    VehicleTransferDetailDao save(VehicleTransferDetailDao partner);
    VehicleTransferDetailDao update(VehicleTransferDetailDao partner);
    void delete(Integer id);
    VehicleTransferDetailDao findByUUID(String uuid);
    VehicleTransferDetailDao findById(Integer id);
    List<VehicleTransferDetailDao> findAllByIds(List<Integer> ids);
    List<VehicleTransferDetailDao> findAll();
}
