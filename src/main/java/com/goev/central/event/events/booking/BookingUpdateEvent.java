package com.goev.central.event.events.booking;

import com.goev.central.dao.booking.BookingDao;
import com.goev.central.event.targets.CustomerTarget;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class BookingUpdateEvent extends Event<BookingDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
        registerEventTargets(CustomerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "BookingUpdateEvent";
    }

}
