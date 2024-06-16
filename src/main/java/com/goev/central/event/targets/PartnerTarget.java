package com.goev.central.event.targets;

import com.goev.central.constant.ApplicationConstants;
import com.goev.lib.event.core.EventChannel;
import com.goev.lib.event.core.EventChannelConfiguration;
import com.goev.lib.event.core.EventTarget;


public class PartnerTarget extends EventTarget {
    private PartnerTarget() {
    }

    public static String getTargetName() {
        return "PARTNER";
    }

    public static PartnerTarget getTarget(EventChannel eventChannel) {
        PartnerTarget partnerTarget = new PartnerTarget();
        partnerTarget.setChannel(eventChannel);
        partnerTarget.setName(getTargetName());
        partnerTarget.setConfig(EventChannelConfiguration.builder()
                .hostName(ApplicationConstants.PARTNER_URL)
                .base("/api/v1/internal")
                .path("/events")
                .authKey(ApplicationConstants.CLIENT_ID)
                .authSecret(ApplicationConstants.CLIENT_SECRET)
                .build());
        return partnerTarget;
    }
}
