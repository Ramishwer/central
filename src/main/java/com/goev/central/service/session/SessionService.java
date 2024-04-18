package com.goev.central.service.session;

import com.goev.central.dto.session.ExchangeTokenRequestDto;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.lib.dto.PasswordCredentialsDto;

public interface SessionService {
    SessionDto createSession(PasswordCredentialsDto credentials);
    SessionDto refreshSessionForSessionUUID(String sessionUUID);
    SessionDetailsDto getSessionDetails(String sessionUUID);
    Boolean deleteSession(String sessionUUID);
    SessionDto createSession(ExchangeTokenRequestDto credentials);
}
