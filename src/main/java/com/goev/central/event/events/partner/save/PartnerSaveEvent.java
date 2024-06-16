package com.goev.central.event.events.partner.save;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PartnerSaveEvent extends Event<PartnerDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerSaveEvent";
    }

}
