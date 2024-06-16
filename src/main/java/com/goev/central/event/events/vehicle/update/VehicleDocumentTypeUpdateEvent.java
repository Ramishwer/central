package com.goev.central.event.events.vehicle.update;

import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class VehicleDocumentTypeUpdateEvent extends Event<VehicleDocumentTypeDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleDocumentTypeUpdateEvent";
    }

}
