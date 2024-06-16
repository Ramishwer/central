package com.goev.central.event.handlers.partner.update;

import com.goev.central.dao.partner.document.PartnerDocumentDao;
import com.goev.central.repository.partner.document.PartnerDocumentRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PartnerDocumentUpdateEventHandler extends EventHandler<PartnerDocumentDao> {

    private final PartnerDocumentRepository partnerDocumentRepository;

    @Override
    public boolean onEvent(Event<PartnerDocumentDao> event) {
        log.info("Data:{}", event.getData());
        PartnerDocumentDao partnerDocumentDao = event.getData();
        if (partnerDocumentDao == null) {
            log.info("PartnerDocument Data Null");
            return false;
        }
        PartnerDocumentDao existing = partnerDocumentRepository.findByUUID(partnerDocumentDao.getUuid());
        if (existing != null) {
            partnerDocumentDao.setId(existing.getId());
            partnerDocumentDao.setUuid(existing.getUuid());
            partnerDocumentRepository.update(partnerDocumentDao);
            return true;
        }
        return false;
    }
}
