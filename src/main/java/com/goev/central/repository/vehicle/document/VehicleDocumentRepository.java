package com.goev.central.repository.vehicle.document;

import com.goev.central.dao.vehicle.document.VehicleDocumentDao;

import java.util.List;
import java.util.Map;

public interface VehicleDocumentRepository {
    VehicleDocumentDao save(VehicleDocumentDao partner);

    VehicleDocumentDao update(VehicleDocumentDao partner);

    void delete(Integer id);

    VehicleDocumentDao findByUUID(String uuid);

    VehicleDocumentDao findById(Integer id);

    List<VehicleDocumentDao> findAllByIds(List<Integer> ids);

    List<VehicleDocumentDao> findAllByVehicleId(Integer vehicleId);

    List<VehicleDocumentDao> findAllByVehicleIdAndDocumentTypeIds(Integer vehicleId, List<Integer> ids);

    Map<Integer, VehicleDocumentDao> getExistingDocumentMap(Integer vehicleId, List<Integer> activeDocumentTypeIds);
}