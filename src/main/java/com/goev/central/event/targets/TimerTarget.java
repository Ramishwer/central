package com.goev.central.event.targets;

import com.goev.central.config.SpringContext;
import com.goev.central.constant.ApplicationConstants;
import com.goev.lib.event.core.EventChannelConfiguration;
import com.goev.lib.event.core.EventTarget;
import com.goev.lib.event.core.impl.APIEventChannel;
import com.goev.lib.event.service.EventProcessor;
import com.goev.lib.services.RestClient;


public class TimerTarget extends EventTarget {
    private TimerTarget() {
    }

    public static String getTargetName() {
        return "TIMER";
    }

    public static TimerTarget getTarget(EventProcessor eventProcessor) {
        APIEventChannel eventChannel = new APIEventChannel();
        eventChannel.init(SpringContext.getBean(RestClient.class));
        TimerTarget timerTarget = new TimerTarget();
        timerTarget.setChannel(eventChannel);
        timerTarget.setName(getTargetName());
        timerTarget.setConfig(EventChannelConfiguration.builder()
                .hostName(ApplicationConstants.TIMER_URL)
                .base("/api/v1/internal")
                .path("/events")
                .authKey(ApplicationConstants.CLIENT_ID)
                .authSecret(ApplicationConstants.CLIENT_SECRET)
                .processor(eventProcessor)
                .build());
        return timerTarget;
    }
}
