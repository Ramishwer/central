package com.goev.central.event.events.location.save;

import com.goev.central.dao.location.LocationDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class LocationSaveEvent extends Event<LocationDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "LocationSaveEvent";
    }

}
