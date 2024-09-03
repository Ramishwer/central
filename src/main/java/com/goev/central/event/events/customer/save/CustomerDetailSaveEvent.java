package com.goev.central.event.events.customer.save;

import com.goev.central.dao.customer.detail.CustomerDetailDao;
import com.goev.central.event.targets.CustomerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CustomerDetailSaveEvent extends Event<CustomerDetailDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(CustomerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "CustomerDetailSaveEvent";
    }

}
