package com.goev.central.service.session.impl;

import com.amazonaws.util.Base64;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.detail.UserDao;
import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.dto.user.auth.AuthCredentialDto;
import com.goev.central.dto.user.auth.AuthCredentialTypeDto;
import com.goev.central.repository.user.detail.UserRepository;
import com.goev.central.repository.user.detail.UserSessionRepository;
import com.goev.central.service.session.SessionService;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.dto.PasswordCredentialsDto;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.exceptions.ResponseException;
import com.goev.lib.services.RestClient;
import com.goev.lib.utilities.ApplicationContext;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final RestClient restClient;
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;

    @Override
    public SessionDto createSession(PasswordCredentialsDto credentials) {
        UserDao user = userRepository.findByEmail(credentials.getUsername());
        if (user == null)
            throw new ResponseException("User does not exist");
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management";
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "Basic "+Base64.encodeAsString(String.valueOf(ApplicationConstants.CLIENT_ID+":"+ApplicationConstants.CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
        String response = restClient.post(url, header,AuthCredentialDto.builder()
                .authCredentialType(AuthCredentialTypeDto.builder()
                        .name(ApplicationConstants.CREDENTIAL_TYPE_NAME)
                        .uuid(ApplicationConstants.CREDENTIAL_TYPE_UUID)
                        .build())
                .build(),true);
        ResponseDto<SessionDto> session = ApplicationConstants.GSON.fromJson(response,new TypeToken<ResponseDto<SessionDto>>(){}.getType());
        return session.getData();
    }

    @Override
    public SessionDto refreshSessionForSessionUUID(String sessionUUID) {

        UserSessionDao userSessionDao= userSessionRepository.findByUUID(sessionUUID);
        if(userSessionDao == null)
            throw new ResponseException("Token Expired");
        if(RequestContext.getRefreshToken() == null)
            throw new ResponseException("Token Expired");
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/"+userSessionDao.getAuthSessionUuid()+"/refresh";
        HttpHeaders header = new HttpHeaders();
        header.set("Refresh-Token", RequestContext.getRefreshToken());
        String response = restClient.get(url, header,true);
        ResponseDto<SessionDto> session = ApplicationConstants.GSON.fromJson(response,new TypeToken<ResponseDto<SessionDto>>(){}.getType());
        return session.getData();
    }

    @Override
    public SessionDetailsDto getSessionDetails(String sessionUUID) {
        UserSessionDao userSessionDao= userSessionRepository.findByUUID(sessionUUID);
        if(userSessionDao == null)
            throw new ResponseException("Token Expired");
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/"+userSessionDao.getAuthSessionUuid();
        HttpHeaders header = new HttpHeaders();
        header.set("Refresh-Token", RequestContext.getRefreshToken());
        String response = restClient.get(url, header,true);
        ResponseDto<SessionDetailsDto> session = ApplicationConstants.GSON.fromJson(response,new TypeToken<ResponseDto<SessionDetailsDto>>(){}.getType());
        return session.getData();
    }

    @Override
    public Boolean deleteSession(String sessionUUID) {
        UserSessionDao userSessionDao= userSessionRepository.findByUUID(sessionUUID);
        if(userSessionDao == null)
            throw new ResponseException("Token Expired");
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/"+userSessionDao.getAuthSessionUuid();
        HttpHeaders header = new HttpHeaders();
        header.set("Refresh-Token", RequestContext.getRefreshToken());
        String response = restClient.delete(url, header,true);
        return true;
    }

    @Override
    public List<SessionDto> getSessions() {
        String authUUID = ApplicationContext.getAuthUUID();
        if(authUUID == null)
            throw new ResponseException("Token Expired");
        UserDao user = userRepository.findByAuthUUID(authUUID);
        List<UserSessionDao> allSessions = userSessionRepository.findAllByUserId(user.getId());
        List<SessionDto> sessionsList = new ArrayList<>();
        allSessions.forEach(x->{
            SessionDto session = SessionDto.builder()
                    .uuid(x.getUuid())
                    .createdTime(x.getCreatedOn())
                    .lastActiveTime(x.getLastActiveTime())
                    .build();
            sessionsList.add(session);
        });
        return sessionsList;
    }
}
