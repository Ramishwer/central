package com.goev.central.event.handlers.vehicle.update;

import com.goev.central.dao.vehicle.asset.VehicleAssetMappingDao;
import com.goev.central.repository.vehicle.asset.VehicleAssetMappingRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleAssetMappingUpdateEventHandler extends EventHandler<VehicleAssetMappingDao> {

    private final VehicleAssetMappingRepository vehicleAssetMappingRepository;

    @Override
    public boolean onEvent(Event<VehicleAssetMappingDao> event) {
        log.info("Data:{}", event.getData());
        VehicleAssetMappingDao vehicleAssetMappingDao = event.getData();
        if (vehicleAssetMappingDao == null) {
            log.info("Vehicle Data Null");
            return false;
        }
        VehicleAssetMappingDao existing = vehicleAssetMappingRepository.findByUUID(vehicleAssetMappingDao.getUuid());
        if (existing != null) {
            vehicleAssetMappingDao.setId(existing.getId());
            vehicleAssetMappingDao.setUuid(existing.getUuid());
            vehicleAssetMappingRepository.update(vehicleAssetMappingDao);
            return true;
        }

        return false;
    }
}
