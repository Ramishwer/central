package com.goev.central.event.handlers.partner.save;

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
public class PartnerDocumentTypeSaveEventHandler extends EventHandler<PartnerDocumentTypeDao> {

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
        if (existing == null) {
            partnerDocumentTypeRepository.save(partnerDocumentTypeDao);
            return true;
        }
        return false;
    }
}
