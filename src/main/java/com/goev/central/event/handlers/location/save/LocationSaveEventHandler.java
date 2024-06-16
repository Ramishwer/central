package com.goev.central.event.handlers.location.save;

import com.goev.central.dao.location.LocationDao;
import com.goev.central.repository.location.LocationRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class LocationSaveEventHandler extends EventHandler<LocationDao> {

    private final LocationRepository locationRepository;

    @Override
    public boolean onEvent(Event<LocationDao> event) {
        log.info("Data:{}", event.getData());
        LocationDao locationDao = event.getData();
        if (locationDao == null) {
            log.info("Location Data Null");
            return false;
        }
        LocationDao existing = locationRepository.findByUUID(locationDao.getUuid());
        if (existing == null) {
            locationRepository.save(locationDao);
            return true;
        }
        return false;
    }
}
