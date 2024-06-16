package com.goev.central.service.auth;

import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.auth.AuthClientDto;
import com.goev.central.dto.auth.AuthUserDto;
import com.goev.central.dto.session.ExchangeTokenRequestDto;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.lib.dto.PasswordCredentialsDto;

public interface AuthService {
    SessionDetailsDto getSessionDetails(UserSessionDao userSessionDao);

    AuthClientDto getClient(String token);

    String createUser(AuthUserDto userDto);

    SessionDto createSessionForToken(ExchangeTokenRequestDto token);

    boolean deleteSession(UserSessionDao userSessionDao);

    SessionDto refreshSession(UserSessionDao userSessionDao);

    SessionDto createSession(PasswordCredentialsDto credentials, String authUUID);
}
