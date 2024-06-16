package com.goev.central.event.events.vehicle.save;

import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class VehicleSaveEvent extends Event<VehicleDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleSaveEvent";
    }

}
