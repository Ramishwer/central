package com.goev.central.controller.session;

import com.goev.central.dto.session.ExchangeTokenRequestDto;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.service.session.SessionService;
import com.goev.lib.dto.PasswordCredentialsDto;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/session-management")
@AllArgsConstructor
public class SessionController {


    private final SessionService sessionService;

    @PostMapping("/sessions/tokens")
    public ResponseDto<SessionDto> createSession(@RequestBody ExchangeTokenRequestDto token) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, sessionService.createSession(token));
    }

    @PostMapping("/sessions")
    public ResponseDto<SessionDto> createSession(@RequestBody PasswordCredentialsDto credentials) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, sessionService.createSession(credentials));
    }

    @PostMapping("/sessions/{session-uuid}/token")
    public ResponseDto<SessionDto> refreshSession(@PathVariable(value = "session-uuid") String sessionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, sessionService.refreshSessionForSessionUUID(sessionUUID));
    }

    @GetMapping("/sessions/{session-uuid}")
    public ResponseDto<SessionDetailsDto> getSession(@PathVariable(value = "session-uuid") String sessionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, sessionService.getSessionDetails(sessionUUID));
    }

    @DeleteMapping("/sessions/{session-uuid}")
    public ResponseDto<Boolean> deleteSession(@PathVariable(value = "session-uuid") String sessionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, sessionService.deleteSession(sessionUUID));
    }
}
