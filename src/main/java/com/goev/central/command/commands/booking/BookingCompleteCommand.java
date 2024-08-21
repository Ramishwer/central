package com.goev.central.command.commands.booking;

import com.goev.lib.command.core.Command;
import com.goev.central.command.targets.PartnerTarget;
import com.goev.central.dao.partner.detail.PartnerDao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class BookingCompleteCommand extends Command<PartnerDao> {

    @Override
    @PostConstruct
    public void init() {
        registerCommandTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "BookingCompleteCommand";
    }
}
