package com.goev.central.event.events.earning.save;
import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PartnerTotalEarningSaveEvent extends Event<PartnerEarningDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerTotalEarningSaveEvent";
    }
}
