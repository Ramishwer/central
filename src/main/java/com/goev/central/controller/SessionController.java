package com.goev.central.controller;

import com.goev.central.dto.partner.PartnerDto;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.service.SessionService;
import com.goev.lib.dto.PasswordCredentialsDto;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/session-management")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/sessions")
    public ResponseDto<List<SessionDto>> getSessions(){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, sessionService.getSessions());
    }
    @PostMapping("/sessions")
    public ResponseDto<SessionDto> createSession(@RequestBody PasswordCredentialsDto credentials){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, sessionService.createSession(credentials));
    }

    @PostMapping("/sessions/{session-uuid}/refresh")
    public ResponseDto<SessionDto> refreshSession(@PathVariable(value = "session-uuid")String sessionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, sessionService.refreshSessionForSessionUUID(sessionUUID));
    }

    @GetMapping("/sessions/{session-uuid}")
    public ResponseDto<SessionDetailsDto> getSession(@PathVariable(value = "session-uuid")String sessionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, sessionService.getSessionDetails(sessionUUID));
    }

    @DeleteMapping("/sessions/{session-uuid}")
    public ResponseDto<Boolean> deleteSession(@PathVariable(value = "session-uuid")String sessionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, sessionService.deleteSession(sessionUUID));
    }
}
