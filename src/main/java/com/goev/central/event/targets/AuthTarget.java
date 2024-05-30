package com.goev.central.event.targets;

import com.goev.central.constant.ApplicationConstants;
import com.goev.lib.event.core.EventChannel;
import com.goev.lib.event.core.EventChannelConfiguration;
import com.goev.lib.event.core.EventTarget;


public class AuthTarget extends EventTarget {
    private AuthTarget(){
    }

    public static String getTargetName(){
        return "AUTH";
    }
    public static AuthTarget getTarget(EventChannel eventChannel){
        AuthTarget authTarget = new AuthTarget();
        authTarget.setChannel(eventChannel);
        authTarget.setName("AUTH");
        authTarget.setConfig(EventChannelConfiguration.builder()
                        .hostName(ApplicationConstants.AUTH_URL)
                        .base("/api/v1/internal")
                        .path("/events")
                .build());
        return authTarget;
    }
}
