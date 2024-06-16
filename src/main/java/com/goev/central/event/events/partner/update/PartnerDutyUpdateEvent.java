package com.goev.central.event.events.partner.update;

import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class PartnerDutyUpdateEvent extends Event<PartnerDutyDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerDutyUpdateEvent";
    }

}
