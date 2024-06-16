package com.goev.central.event.events.vehicle.update;

import com.goev.central.dao.partner.document.PartnerDocumentDao;
import com.goev.central.dao.vehicle.document.VehicleDocumentDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class VehicleDocumentUpdateEvent extends Event<VehicleDocumentDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "VehicleDocumentUpdateEvent";
    }

}
