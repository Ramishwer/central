package com.goev.central.repository.vehicle.detail;

import com.goev.central.dao.vehicle.detail.VehicleSegmentDao;

import java.util.List;

public interface VehicleSegmentRepository {
    VehicleSegmentDao save(VehicleSegmentDao segment);

    VehicleSegmentDao update(VehicleSegmentDao segment);

    void delete(Integer id);

    VehicleSegmentDao findByUUID(String uuid);

    VehicleSegmentDao findById(Integer id);

    List<VehicleSegmentDao> findAllByIds(List<Integer> ids);

    List<VehicleSegmentDao> findAllActive();
}