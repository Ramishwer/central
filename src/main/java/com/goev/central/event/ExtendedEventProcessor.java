package com.goev.central.event;

import com.goev.central.dto.TimerRequestDto;
import com.goev.central.event.events.TimerEvent;
import com.goev.central.event.targets.TimerTarget;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventTarget;
import com.goev.lib.event.service.impl.SimpleEventProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

@Slf4j
@AllArgsConstructor
public class ExtendedEventProcessor extends SimpleEventProcessor {
    @Override
    public boolean scheduleEvent(Event event) {
        if (event.getExecutionTime() <= DateTime.now().getMillis())
            return sendEvent(event);
        EventTarget target = TimerTarget.getTarget(this);

        TimerEvent timerEvent = new TimerEvent();
        timerEvent.setData(TimerRequestDto.builder()
                .eventName(event.getName())
                .data(event.getData())
                .targets(event.getEventTargets())
                .build());
        target.getChannel().send(timerEvent, target);
        return true;
    }
}
