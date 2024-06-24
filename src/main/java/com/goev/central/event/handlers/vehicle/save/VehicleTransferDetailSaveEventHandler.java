package com.goev.central.event.handlers.vehicle.save;

import com.goev.central.dao.vehicle.transfer.VehicleTransferDetailDao;
import com.goev.central.repository.vehicle.transfer.VehicleTransferDetailRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleTransferDetailSaveEventHandler extends EventHandler<VehicleTransferDetailDao> {

    private final VehicleTransferDetailRepository vehicleTransferDetailRepository;

    @Override
    public boolean onEvent(Event<VehicleTransferDetailDao> event) {
        log.info("Data:{}", event.getData());
        VehicleTransferDetailDao vehicleTransferDetailDao = event.getData();
        if (vehicleTransferDetailDao == null) {
            log.info("Vehicle Data Null");
            return false;
        }
        VehicleTransferDetailDao existing = vehicleTransferDetailRepository.findByUUID(vehicleTransferDetailDao.getUuid());
        if (existing == null) {
            vehicleTransferDetailRepository.save(vehicleTransferDetailDao);
            return true;
        }
        return false;
    }
}
