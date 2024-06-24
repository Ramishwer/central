package com.goev.central.event.handlers.vehicle.save;

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
public class VehicleDocumentTypeSaveEventHandler extends EventHandler<VehicleDocumentTypeDao> {

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
        if (existing == null) {
            vehicleDocumentTypeRepository.save(vehicleDocumentTypeDao);
            return true;
        }
        return false;
    }
}
