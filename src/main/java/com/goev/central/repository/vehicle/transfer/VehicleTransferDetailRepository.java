package com.goev.central.repository.vehicle.transfer;

import com.goev.central.dao.vehicle.transfer.VehicleTransferDetailDao;

import java.util.List;

public interface VehicleTransferDetailRepository {
    VehicleTransferDetailDao save(VehicleTransferDetailDao partner);

    VehicleTransferDetailDao update(VehicleTransferDetailDao partner);

    void delete(Integer id);

    VehicleTransferDetailDao findByUUID(String uuid);

    VehicleTransferDetailDao findById(Integer id);

    List<VehicleTransferDetailDao> findAllByIds(List<Integer> ids);

    List<VehicleTransferDetailDao> findAllActive();

    List<VehicleTransferDetailDao> findAllByVehicleId(Integer vehicleId);
}
