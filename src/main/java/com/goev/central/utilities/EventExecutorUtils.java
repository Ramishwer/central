package com.goev.central.utilities;

import com.goev.central.config.SpringContext;
import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.event.events.partner.save.PartnerDetailSaveEvent;
import com.goev.central.event.events.partner.update.PartnerDetailUpdateEvent;
import com.goev.lib.event.service.EventProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventExecutorUtils {

    public boolean fireEvent(String event, Object data) {

        switch (event) {
            case "PartnerDetailUpdateEvent" -> {
                PartnerDetailUpdateEvent partnerDetailUpdateEvent = new PartnerDetailUpdateEvent();
                partnerDetailUpdateEvent.setData((PartnerDetailDao) data);
                partnerDetailUpdateEvent.setExecutionTime(DateTime.now().getMillis());
                SpringContext.getBean(EventProcessor.class).sendEvent(partnerDetailUpdateEvent);
                return true;
            }
            case "PartnerDetailSaveEvent" -> {
                PartnerDetailSaveEvent partnerDetailSaveEvent = new PartnerDetailSaveEvent();
                partnerDetailSaveEvent.setData((PartnerDetailDao) data);
                partnerDetailSaveEvent.setExecutionTime(DateTime.now().getMillis());
                SpringContext.getBean(EventProcessor.class).sendEvent(partnerDetailSaveEvent);
                return true;
            }
            default -> {
                return false;
            }
        }

    }
}
