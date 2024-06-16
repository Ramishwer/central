package com.goev.central.event.events.vehicle.save;

import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class VehicleDocumentTypeSaveEvent extends Event<VehicleDocumentTypeDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleAssetTransferDetailUpdateEvent";
    }

}
