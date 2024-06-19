package com.goev.central.event.handlers.partner;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.partner.detail.PartnerDetailService;
import com.goev.central.service.partner.detail.PartnerService;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PartnerOnboardingStatusCheckEventHandler extends EventHandler<String> {

    private final PartnerDetailService partnerDetailService;

    @Override
    public boolean onEvent(Event<String> event) {
        log.info("Data:{}", event.getData());
        String partnerUUID = event.getData();
        if (partnerUUID == null) {
            log.info("Partner Data Null");
            return false;
        }
       return  partnerDetailService.updateOnboardingStatus(partnerUUID);
    }
}
