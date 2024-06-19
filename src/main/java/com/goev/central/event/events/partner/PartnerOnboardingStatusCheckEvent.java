package com.goev.central.event.events.partner;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.event.targets.CentralTarget;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PartnerOnboardingStatusCheckEvent extends Event<String> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(CentralTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerOnboardingStatusCheckEvent";
    }

}
