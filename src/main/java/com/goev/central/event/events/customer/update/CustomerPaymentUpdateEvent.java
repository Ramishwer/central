package com.goev.central.event.events.customer.update;

import com.goev.central.dao.customer.payment.CustomerPaymentDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CustomerPaymentUpdateEvent extends Event<CustomerPaymentDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "CustomerPaymentUpdateEvent";
    }

}
