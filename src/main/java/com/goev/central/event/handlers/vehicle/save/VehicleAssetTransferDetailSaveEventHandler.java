package com.goev.central.event.handlers.vehicle.save;

import com.goev.central.dao.vehicle.transfer.VehicleAssetTransferDetailDao;
import com.goev.central.repository.vehicle.transfer.VehicleAssetTransferDetailRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleAssetTransferDetailSaveEventHandler extends EventHandler<VehicleAssetTransferDetailDao> {

    private final VehicleAssetTransferDetailRepository vehicleAssetTransferDetailRepository;

    @Override
    public boolean onEvent(Event<VehicleAssetTransferDetailDao> event) {
        log.info("Data:{}", event.getData());
        VehicleAssetTransferDetailDao vehicleAssetTransferDetailDao = event.getData();
        if (vehicleAssetTransferDetailDao == null) {
            log.info("Vehicle Data Null");
            return false;
        }
        VehicleAssetTransferDetailDao existing = vehicleAssetTransferDetailRepository.findByUUID(vehicleAssetTransferDetailDao.getUuid());
        if (existing == null) {
            vehicleAssetTransferDetailRepository.save(vehicleAssetTransferDetailDao);
            return true;
        }

        return false;
    }
}
