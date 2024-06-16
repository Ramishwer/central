package com.goev.central.event.events.vehicle.update;

import com.goev.central.dao.vehicle.detail.VehicleDetailDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class VehicleDetailUpdateEvent extends Event<VehicleDetailDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleDetailUpdateEvent";
    }

}
