package com.goev.central.event.events.customer.save;

import com.goev.central.dao.customer.payment.CustomerPaymentDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class CustomerPaymentSaveEvent extends Event<CustomerPaymentDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "CustomerPaymentSaveEvent";
    }

}
