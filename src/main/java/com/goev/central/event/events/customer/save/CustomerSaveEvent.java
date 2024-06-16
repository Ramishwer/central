package com.goev.central.event.events.customer.save;

import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CustomerSaveEvent extends Event<CustomerDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "CustomerSaveEvent";
    }

}
