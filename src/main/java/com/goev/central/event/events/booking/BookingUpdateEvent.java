package com.goev.central.event.events.booking;

import com.goev.central.dao.booking.BookingDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class BookingUpdateEvent extends Event<BookingDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "BookingUpdateEvent";
    }

}
