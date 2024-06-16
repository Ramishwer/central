package com.goev.central.event.events.partner.save;

import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PartnerShiftSaveEvent extends Event<PartnerShiftDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerShiftSaveEvent";
    }

}
