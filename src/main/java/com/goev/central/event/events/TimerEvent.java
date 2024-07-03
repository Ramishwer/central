package com.goev.central.event.events;

import com.goev.lib.event.core.Event;
import com.goev.central.dto.TimerRequestDto;
import com.goev.central.event.targets.TimerTarget;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TimerEvent extends Event<TimerRequestDto> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(TimerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "TimerEvent";
    }

}
