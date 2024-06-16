package com.goev.central.event.handlers.vehicle.update;

import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.repository.vehicle.document.VehicleDocumentTypeRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleDocumentTypeUpdateEventHandler extends EventHandler<VehicleDocumentTypeDao> {

    private final VehicleDocumentTypeRepository vehicleDocumentTypeRepository;

    @Override
    public boolean onEvent(Event<VehicleDocumentTypeDao> event) {
        log.info("Data:{}", event.getData());
        VehicleDocumentTypeDao vehicleDocumentTypeDao = event.getData();
        if (vehicleDocumentTypeDao == null) {
            log.info("VehicleDocumentType Data Null");
            return false;
        }
        VehicleDocumentTypeDao existing = vehicleDocumentTypeRepository.findByUUID(vehicleDocumentTypeDao.getUuid());
        if (existing != null) {
            vehicleDocumentTypeDao.setId(existing.getId());
            vehicleDocumentTypeDao.setUuid(existing.getUuid());
            vehicleDocumentTypeRepository.update(vehicleDocumentTypeDao);
            return true;
        }
        return false;
    }
}
