package com.goev.central.event.handlers.partner.save;

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
public class PartnerDocumentSaveEventHandler extends EventHandler<PartnerDocumentDao> {

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
        if (existing == null) {
            partnerDocumentRepository.save(partnerDocumentDao);
            return true;
        }
        return false;
    }
}
