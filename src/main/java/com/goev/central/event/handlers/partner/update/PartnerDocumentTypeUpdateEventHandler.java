package com.goev.central.event.handlers.partner.update;

import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.repository.partner.document.PartnerDocumentTypeRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PartnerDocumentTypeUpdateEventHandler extends EventHandler<PartnerDocumentTypeDao> {

    private final PartnerDocumentTypeRepository partnerDocumentTypeRepository;

    @Override
    public boolean onEvent(Event<PartnerDocumentTypeDao> event) {
        log.info("Data:{}", event.getData());
        PartnerDocumentTypeDao partnerDocumentTypeDao = event.getData();
        if (partnerDocumentTypeDao == null) {
            log.info("PartnerDocumentType Data Null");
            return false;
        }
        PartnerDocumentTypeDao existing = partnerDocumentTypeRepository.findByUUID(partnerDocumentTypeDao.getUuid());
        if (existing != null) {
            partnerDocumentTypeDao.setId(existing.getId());
            partnerDocumentTypeDao.setUuid(existing.getUuid());
            partnerDocumentTypeRepository.update(partnerDocumentTypeDao);
            return true;
        }
        return false;
    }
}
