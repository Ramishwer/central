package com.goev.central.event.handlers.partner.save;

import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.repository.partner.detail.PartnerDetailRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PartnerDetailSaveEventHandler extends EventHandler<PartnerDetailDao> {

    private final PartnerDetailRepository partnerDetailRepository;

    @Override
    public boolean onEvent(Event<PartnerDetailDao> event) {
        log.info("Data:{}", event.getData());
        PartnerDetailDao partnerDetailDao = event.getData();
        if (partnerDetailDao == null) {
            log.info("Partner Data Null");
            return false;
        }
        PartnerDetailDao existing = partnerDetailRepository.findByUUID(partnerDetailDao.getUuid());
        if (existing == null) {
            partnerDetailRepository.save(partnerDetailDao);
            return true;
        }
        return false;
    }
}
