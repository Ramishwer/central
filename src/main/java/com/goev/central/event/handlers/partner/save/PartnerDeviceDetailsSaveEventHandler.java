package com.goev.central.event.handlers.partner.save;

import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.dao.partner.detail.PartnerDeviceDao;
import com.goev.central.event.events.partner.save.PartnerDeviceDetailsSaveEvent;
import com.goev.central.repository.partner.detail.PartnerDeviceRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PartnerDeviceDetailsSaveEventHandler extends EventHandler<PartnerDeviceDao> {
    final PartnerDeviceRepository partnerDeviceRepository;
    @Override
    public boolean onEvent(Event<PartnerDeviceDao> event) {
        log.info("Data:{}", event.getData());
        PartnerDeviceDao partnerDeviceDao = event.getData();
        if (partnerDeviceDao == null) {
            log.info("Partner Data Null");
            return false;
        }
        PartnerDeviceDao existing = partnerDeviceRepository.findByUUID(partnerDeviceDao.getUuid());
        if (existing == null) {
            partnerDeviceRepository.save(partnerDeviceDao);
            return true;
        }
        return false;
    }
}
