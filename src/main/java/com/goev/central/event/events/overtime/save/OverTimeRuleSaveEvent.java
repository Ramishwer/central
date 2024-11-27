package com.goev.central.event.events.overtime.save;
import com.goev.central.dao.overtime.OverTimeRuleDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class OverTimeRuleSaveEvent extends Event<OverTimeRuleDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "OverTimeRuleSaveEvent";
    }
}
