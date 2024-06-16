package com.goev.central.event.handlers.vehicle.update;

import com.goev.central.dao.vehicle.detail.VehicleDetailDao;
import com.goev.central.repository.vehicle.detail.VehicleDetailRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleDetailUpdateEventHandler extends EventHandler<VehicleDetailDao> {

    private final VehicleDetailRepository vehicleDetailRepository;

    @Override
    public boolean onEvent(Event<VehicleDetailDao> event) {
        log.info("Data:{}", event.getData());
        VehicleDetailDao vehicleDetailDao = event.getData();
        if (vehicleDetailDao == null) {
            log.info("Vehicle Data Null");
            return false;
        }
        VehicleDetailDao existing = vehicleDetailRepository.findByUUID(vehicleDetailDao.getUuid());
        if (existing != null) {
            vehicleDetailDao.setId(existing.getId());
            vehicleDetailDao.setUuid(existing.getUuid());
            vehicleDetailRepository.update(vehicleDetailDao);
            return true;
        }

        return false;
    }
}
