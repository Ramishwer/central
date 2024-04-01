
package com.goev.central.repository.vehicle.detail;


import com.goev.central.dao.vehicle.detail.VehicleLeasingAgencyDao;

import java.util.List;

public interface VehicleLeasingAgencyRepository {
    VehicleLeasingAgencyDao save(VehicleLeasingAgencyDao agency);
    VehicleLeasingAgencyDao update(VehicleLeasingAgencyDao agency);
    void delete(Integer id);
    VehicleLeasingAgencyDao findByUUID(String uuid);
    VehicleLeasingAgencyDao findById(Integer id);
    List<VehicleLeasingAgencyDao> findAllByIds(List<Integer> ids);
    List<VehicleLeasingAgencyDao> findAll();
}


