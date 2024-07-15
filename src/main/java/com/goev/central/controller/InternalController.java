package com.goev.central.controller;

import com.goev.central.config.SpringContext;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.command.service.CommandProcessor;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import com.goev.lib.event.service.EventProcessor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class InternalController {

    @PostMapping("/api/v1/internal/events")
    public ResponseDto<Boolean> receivedEvent(@RequestBody String event, @RequestParam("name") String name, HttpServletRequest request) {

        log.info("Received Event :{}", event);
        RequestContext.setRequestSource("EVENT");
        RequestContext.setAuthUUID(request.getHeader("authUUID"));
        return new ResponseDto<>(
                StatusDto.builder().message("SUCCESS").build(), 200,
                SpringContext.getBean(EventProcessor.class).receiveEvent(event, name));
    }

    @PostMapping("/api/v1/internal/commands")
    public ResponseDto<String> receivedCommand(@RequestBody String command, @RequestParam("name") String name, HttpServletRequest request) {
        log.info("Received Event :{}", command);
        RequestContext.setRequestSource("COMMAND");
        RequestContext.setAuthUUID(request.getHeader("authUUID"));
        return new ResponseDto<>(
                StatusDto.builder().message("SUCCESS").build(), 200,
                SpringContext.getBean(CommandProcessor.class).receiveCommand(command, name));
    }
}
