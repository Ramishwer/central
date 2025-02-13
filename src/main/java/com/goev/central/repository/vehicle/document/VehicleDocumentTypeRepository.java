package com.goev.central.repository.vehicle.document;

import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;

import java.util.List;

public interface VehicleDocumentTypeRepository {
    VehicleDocumentTypeDao save(VehicleDocumentTypeDao partner);

    VehicleDocumentTypeDao update(VehicleDocumentTypeDao partner);

    void delete(Integer id);

    VehicleDocumentTypeDao findByUUID(String uuid);

    VehicleDocumentTypeDao findById(Integer id);

    List<VehicleDocumentTypeDao> findAllByIds(List<Integer> ids);

    List<VehicleDocumentTypeDao> findAllActive();


}
