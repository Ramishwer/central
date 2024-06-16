package com.goev.central.event.events.vehicle.save;

import com.goev.central.dao.vehicle.transfer.VehicleTransferDetailDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class VehicleTransferDetailSaveEvent extends Event<VehicleTransferDetailDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleTransferDetailSaveEvent";
    }

}
