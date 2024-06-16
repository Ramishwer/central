package com.goev.central.service.session.impl;

import com.amazonaws.util.Base64;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.session.ExchangeTokenRequestDto;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.dto.user.authentication.AuthCredentialDto;
import com.goev.central.dto.user.authentication.AuthCredentialTypeDto;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.repository.user.detail.UserSessionRepository;
import com.goev.central.service.auth.AuthService;
import com.goev.central.service.session.SessionService;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.dto.PasswordCredentialsDto;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.exceptions.ResponseException;
import com.goev.lib.services.RestClient;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final AuthService authService;

    @Override
    public SessionDto createSession(PasswordCredentialsDto credentials) {
        UserDao user = userRepository.findByEmail(credentials.getUsername());
        if (user == null|| user.getAuthUuid() == null)
            throw new ResponseException("User does not exist");
        SessionDto sessionDto = authService.createSession(credentials,user.getAuthUuid());
        if (sessionDto == null)
            throw new ResponseException("User does not exist");
        UserSessionDao sessionDao = new UserSessionDao();
        sessionDao.setAuthSessionUuid(sessionDto.getUuid());
        sessionDao.setUserId(user.getId());
        sessionDao.setLastActiveTime(DateTime.now());
        sessionDao = userSessionRepository.save(sessionDao);

        return SessionDto.builder()
                .accessToken(sessionDto.getAccessToken())
                .refreshToken(sessionDto.getRefreshToken())
                .expiresIn(sessionDto.getExpiresIn())
                .refreshExpiresIn(sessionDto.getRefreshExpiresIn())
                .userUUID(user.getUuid())
                .uuid(sessionDao.getUuid())
                .build();
    }

    @Override
    public SessionDto refreshSessionForSessionUUID(String sessionUUID) {

        UserSessionDao userSessionDao = userSessionRepository.findByUUID(sessionUUID);
        if (userSessionDao == null)
            throw new ResponseException("Token Expired");
        if (RequestContext.getRefreshToken() == null)
            throw new ResponseException("Token Expired");
        SessionDto sessionDto = authService.refreshSession(userSessionDao);
        if (sessionDto == null)
            throw new ResponseException("Token Expired");

        UserSessionDao sessionDao = new UserSessionDao();
        sessionDao.setAuthSessionUuid(sessionDto.getUuid());
        sessionDao.setUserId(sessionDao.getUserId());
        sessionDao.setLastActiveTime(DateTime.now());
        sessionDao = userSessionRepository.save(sessionDao);
        userSessionRepository.delete(userSessionDao.getId());

        UserDao user = userRepository.findById(userSessionDao.getUserId());
        return SessionDto.builder()
                .accessToken(sessionDto.getAccessToken())
                .refreshToken(sessionDto.getRefreshToken())
                .expiresIn(sessionDto.getExpiresIn())
                .refreshExpiresIn(sessionDto.getRefreshExpiresIn())
                .userUUID(user.getUuid())
                .uuid(sessionDao.getUuid())
                .build();
    }

    @Override
    public SessionDetailsDto getSessionDetails(String sessionUUID) {
        UserSessionDao userSessionDao = RequestContext.getUserSession();
        if (userSessionDao == null)
            throw new ResponseException("Token Expired");
        userSessionDao.setLastActiveTime(DateTime.now());
        userSessionDao = userSessionRepository.update(userSessionDao);

        UserDao user = userRepository.findById(userSessionDao.getUserId());
        return SessionDetailsDto.builder()
                .details(SessionDto.builder()
                        .userUUID(user.getUuid())
                        .uuid(userSessionDao.getUuid())
                        .build())
                .build();
    }

    @Override
    public Boolean deleteSession(String sessionUUID) {
        UserSessionDao userSessionDao = RequestContext.getUserSession();
        if (userSessionDao == null)
            throw new ResponseException("Token Expired");
        userSessionRepository.delete(userSessionDao.getId());
        return authService.deleteSession(userSessionDao);

    }

    @Override
    public SessionDto createSession(ExchangeTokenRequestDto token) {

        SessionDto sessionDto = authService.createSessionForToken(token);
        if (sessionDto == null || sessionDto.getAuthUUID() == null)
            throw new ResponseException("User does not exist");

        UserDao user = userRepository.findByAuthUUID(sessionDto.getAuthUUID());
        if (user == null)
            throw new ResponseException("User does not exist");

        UserSessionDao sessionDao = new UserSessionDao();
        sessionDao.setAuthSessionUuid(sessionDto.getUuid());
        sessionDao.setUserId(user.getId());
        sessionDao.setLastActiveTime(DateTime.now());
        sessionDao = userSessionRepository.save(sessionDao);

        return SessionDto.builder()
                .accessToken(sessionDto.getAccessToken())
                .refreshToken(sessionDto.getRefreshToken())
                .expiresIn(sessionDto.getExpiresIn())
                .refreshExpiresIn(sessionDto.getRefreshExpiresIn())
                .userUUID(user.getUuid())
                .uuid(sessionDao.getUuid())
                .build();
    }
}
