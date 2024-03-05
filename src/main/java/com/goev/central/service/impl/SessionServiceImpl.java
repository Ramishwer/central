package com.goev.central.service.impl;

import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.service.SessionService;
import com.goev.lib.dto.PasswordCredentialsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {
    @Override
    public SessionDto createSession(PasswordCredentialsDto credentials) {
        return null;
    }

    @Override
    public SessionDto refreshSessionForSessionUUID(String sessionUUID) {
        return null;
    }

    @Override
    public SessionDetailsDto getSessionDetails(String sessionUUID) {
        return null;
    }

    @Override
    public Boolean deleteSession(String sessionUUID) {
        return null;
    }

    @Override
    public List<SessionDto> getSessions() {
        return new ArrayList<>();
    }
}
