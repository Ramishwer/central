package com.goev.central.service.auth.impl;

import com.amazonaws.util.Base64;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.auth.AuthClientDto;
import com.goev.central.dto.auth.AuthCredentialDto;
import com.goev.central.dto.auth.AuthCredentialTypeDto;
import com.goev.central.dto.auth.AuthUserDto;
import com.goev.central.dto.session.ExchangeTokenRequestDto;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.dto.session.SessionDto;
import com.goev.central.service.auth.AuthService;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.dto.PasswordCredentialsDto;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.services.RestClient;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final RestClient restClient;

    @Override
    public SessionDetailsDto getSessionDetails(UserSessionDao userSessionDao) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/sessions/" + userSessionDao.getAuthSessionUuid();
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", RequestContext.getAccessToken());
        String responseStr = restClient.get(url, header, String.class, true);
        ResponseDto<SessionDetailsDto> session = ApplicationConstants.GSON.fromJson(responseStr, new TypeToken<ResponseDto<SessionDetailsDto>>() {
        }.getType());
        if (session == null)
            return null;
        return session.getData();
    }


    @Override
    public AuthClientDto getClient(String token) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/client-management/clients";
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", token);
        String responseStr = restClient.get(url, header, String.class, true);
        ResponseDto<AuthClientDto> client = ApplicationConstants.GSON.fromJson(responseStr, new TypeToken<ResponseDto<AuthClientDto>>() {
        }.getType());
        if (client == null)
            return null;
        return client.getData();
    }

    @Override
    public String createUser(AuthUserDto userDto) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/user-management/users";
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeAsString((ApplicationConstants.CLIENT_ID + ":" + ApplicationConstants.CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
        String responseStr = restClient.post(url, header, userDto, String.class, true);
        ResponseDto<AuthUserDto> user = ApplicationConstants.GSON.fromJson(responseStr, new TypeToken<ResponseDto<AuthUserDto>>() {
        }.getType());
        if (user == null || user.getData() == null)
            return null;
        return user.getData().getUuid();
    }

    @Override
    public String updateUser(AuthUserDto userDto) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/user-management/users/" + userDto.getUuid();
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeAsString((ApplicationConstants.CLIENT_ID + ":" + ApplicationConstants.CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
        String responseStr = restClient.put(url, header, userDto, String.class, true);
        ResponseDto<AuthUserDto> user = ApplicationConstants.GSON.fromJson(responseStr, new TypeToken<ResponseDto<AuthUserDto>>() {
        }.getType());
        if (user == null || user.getData() == null)
            return null;
        return user.getData().getUuid();
    }

    @Override
    public SessionDto createSessionForToken(ExchangeTokenRequestDto token) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/sessions/tokens";
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeAsString((ApplicationConstants.CLIENT_ID + ":" + ApplicationConstants.CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
        String response = restClient.post(url, header, token, String.class, true);
        ResponseDto<SessionDto> session = ApplicationConstants.GSON.fromJson(response, new TypeToken<ResponseDto<SessionDto>>() {
        }.getType());

        if (session == null)
            return null;
        return session.getData();
    }

    @Override
    public boolean deleteSession(UserSessionDao userSessionDao) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/sessions/" + userSessionDao.getAuthSessionUuid();
        HttpHeaders header = new HttpHeaders();
        header.set("Refresh-Token", RequestContext.getRefreshToken());
        header.set("Authorization", RequestContext.getAccessToken());
        String response = restClient.delete(url, header, String.class, true);
        return response != null;
    }


    @Override
    public SessionDto refreshSession(UserSessionDao userSessionDao) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/sessions/" + userSessionDao.getAuthSessionUuid() + "/token";
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeAsString((ApplicationConstants.CLIENT_ID + ":" + ApplicationConstants.CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
        header.set("Refresh-Token", RequestContext.getRefreshToken());
        String response = restClient.get(url, header, String.class, true);
        ResponseDto<SessionDto> session = ApplicationConstants.GSON.fromJson(response, new TypeToken<ResponseDto<SessionDto>>() {
        }.getType());
        if (session == null)
            return null;
        return session.getData();
    }

    @Override
    public SessionDto createSession(PasswordCredentialsDto credentials, String authUUID) {
        String url = ApplicationConstants.AUTH_URL + "/api/v1/session-management/sessions";
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.encodeAsString((ApplicationConstants.CLIENT_ID + ":" + ApplicationConstants.CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
        String response = restClient.post(url, header, AuthCredentialDto.builder()
                .authCredentialType(AuthCredentialTypeDto.builder()
                        .name(ApplicationConstants.CREDENTIAL_TYPE_NAME)
                        .uuid(ApplicationConstants.CREDENTIAL_TYPE_UUID)
                        .build())
                .authKey(credentials.getUsername())
                .authSecret(credentials.getPassword())
                .authUUID(authUUID)
                .build(), String.class, true);
        ResponseDto<SessionDto> session = ApplicationConstants.GSON.fromJson(response, new TypeToken<ResponseDto<SessionDto>>() {
        }.getType());

        if (session == null)
            return null;
        return session.getData();
    }
}
