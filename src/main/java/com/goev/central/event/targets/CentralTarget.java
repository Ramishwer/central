package com.goev.central.event.targets;

import com.goev.lib.event.core.EventChannel;
import com.goev.lib.event.core.EventChannelConfiguration;
import com.goev.lib.event.core.EventTarget;
import com.goev.lib.event.core.impl.SelfEventChannel;
import com.goev.lib.event.service.EventProcessor;


public class CentralTarget extends EventTarget {
    private CentralTarget() {
    }

    public static String getTargetName() {
        return "CENTRAL";
    }

    public static CentralTarget getTarget(EventProcessor eventProcessor) {
        SelfEventChannel eventChannel = new SelfEventChannel();
        eventChannel.init();
        CentralTarget centralTarget = new CentralTarget();
        centralTarget.setChannel(eventChannel);
        centralTarget.setName(getTargetName());
        centralTarget.setConfig(EventChannelConfiguration.builder()
                .processor(eventProcessor)
                .build());

        return centralTarget;
    }
}
