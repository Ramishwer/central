package com.goev.central.event.events;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class PartnerUpdateEvent extends Event<PartnerDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerUpdateEvent";
    }

}
