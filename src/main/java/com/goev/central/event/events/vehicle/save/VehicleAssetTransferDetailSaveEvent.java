package com.goev.central.event.events.vehicle.save;

import com.goev.central.dao.vehicle.transfer.VehicleAssetTransferDetailDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class VehicleAssetTransferDetailSaveEvent extends Event<VehicleAssetTransferDetailDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleAssetTransferDetailSaveEvent";
    }

}
