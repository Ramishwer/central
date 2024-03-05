package com.goev.central.service;

import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.lib.dto.PasswordCredentialsDto;

public interface SessionService {
    SessionDto createSession(PasswordCredentialsDto credentials);
    SessionDto refreshSessionForSessionUUID(String sessionUUID);
    SessionDetailsDto getSessionDetails(String sessionUUID);
    Boolean deleteSession(String sessionUUID);

}
