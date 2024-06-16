package com.goev.central.event.events.partner.update;

import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class PartnerShiftUpdateEvent extends Event<PartnerShiftDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerShiftUpdateEvent";
    }

}
