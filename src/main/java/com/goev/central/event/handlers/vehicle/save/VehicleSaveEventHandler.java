package com.goev.central.event.handlers.vehicle.save;

import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleSaveEventHandler extends EventHandler<VehicleDao> {

    private final VehicleRepository vehicleRepository;

    @Override
    public boolean onEvent(Event<VehicleDao> event) {
        log.info("Data:{}", event.getData());
        VehicleDao vehicleDao = event.getData();
        if (vehicleDao == null) {
            log.info("Vehicle Data Null");
            return false;
        }
        VehicleDao existing = vehicleRepository.findByUUID(vehicleDao.getUuid());
        if (existing == null) {
            vehicleRepository.save(vehicleDao);
            return true;
        }
        return false;
    }
}
