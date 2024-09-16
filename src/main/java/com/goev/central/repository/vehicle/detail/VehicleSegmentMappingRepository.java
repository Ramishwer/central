package com.goev.central.repository.vehicle.detail;

import com.goev.central.dao.vehicle.detail.VehicleSegmentMappingDao;

import java.util.List;

public interface VehicleSegmentMappingRepository {
    VehicleSegmentMappingDao save(VehicleSegmentMappingDao vehicle);

    VehicleSegmentMappingDao update(VehicleSegmentMappingDao vehicle);

    void delete(Integer id);

    VehicleSegmentMappingDao findByUUID(String uuid);

    VehicleSegmentMappingDao findById(Integer id);

    List<VehicleSegmentMappingDao> findAllByIds(List<Integer> ids);

    List<VehicleSegmentMappingDao> findAllActive();

    List<VehicleSegmentMappingDao> findAllBySegmentId(Integer vehicleSegmentId);

    List<VehicleSegmentMappingDao> findAllByVehicleId(Integer vehicleId);
}