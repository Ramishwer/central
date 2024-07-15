package com.goev.central.command.targets;

import com.goev.lib.command.core.CommandChannelConfiguration;
import com.goev.lib.command.core.CommandTarget;
import com.goev.lib.command.core.impl.SelfCommandChannel;
import com.goev.lib.command.service.CommandProcessor;


public class CentralTarget extends CommandTarget {
    private CentralTarget() {
    }

    public static String getTargetName() {
        return "CENTRAL";
    }

    public static CentralTarget getTarget(CommandProcessor commandProcessor) {
        SelfCommandChannel commandChannel = new SelfCommandChannel();
        commandChannel.init();
        CentralTarget centralTarget = new CentralTarget();
        centralTarget.setChannel(commandChannel);
        centralTarget.setName(getTargetName());
        centralTarget.setConfig(CommandChannelConfiguration.builder()
                .processor(commandProcessor)
                .build());

        return centralTarget;
    }
}
