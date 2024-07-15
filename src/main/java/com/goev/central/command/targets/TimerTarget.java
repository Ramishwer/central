package com.goev.central.command.targets;

import com.goev.central.config.SpringContext;
import com.goev.central.constant.ApplicationConstants;
import com.goev.lib.command.core.CommandChannelConfiguration;
import com.goev.lib.command.core.CommandTarget;
import com.goev.lib.command.core.impl.APICommandChannel;
import com.goev.lib.command.service.CommandProcessor;
import com.goev.lib.event.core.EventChannelConfiguration;
import com.goev.lib.event.core.EventTarget;
import com.goev.lib.event.core.impl.APIEventChannel;
import com.goev.lib.event.service.EventProcessor;
import com.goev.lib.services.RestClient;


public class TimerTarget extends CommandTarget {
    private TimerTarget() {
    }

    public static String getTargetName() {
        return "TIMER";
    }

    public static TimerTarget getTarget(CommandProcessor commandProcessor) {
        APICommandChannel commandChannel = new APICommandChannel();
        commandChannel.init(SpringContext.getBean(RestClient.class));
        TimerTarget timerTarget = new TimerTarget();
        timerTarget.setChannel(commandChannel);
        timerTarget.setName(getTargetName());
        timerTarget.setConfig(CommandChannelConfiguration.builder()
                .hostName(ApplicationConstants.TIMER_URL)
                .base("/api/v1/internal")
                .path("/events")
                .authKey(ApplicationConstants.CLIENT_ID)
                .authSecret(ApplicationConstants.CLIENT_SECRET)
                .processor(commandProcessor)
                .build());
        return timerTarget;
    }
}
