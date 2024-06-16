package com.goev.central.event.events.partner.save;

import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class PartnerDocumentTypeSaveEvent extends Event<PartnerDocumentTypeDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "PartnerDocumentTypeSaveEvent";
    }

}
