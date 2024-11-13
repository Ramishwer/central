package com.goev.central.event.events.earning.update;
import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EarningRuleUpdateEvent extends Event<EarningRuleDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "EarningRuleUpdateEvent";
    }
}
