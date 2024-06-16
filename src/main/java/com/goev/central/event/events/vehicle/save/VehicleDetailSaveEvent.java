package com.goev.central.event.events.vehicle.save;

import com.goev.central.dao.vehicle.detail.VehicleDetailDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class VehicleDetailSaveEvent extends Event<VehicleDetailDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleDetailSaveEvent";
    }

}
