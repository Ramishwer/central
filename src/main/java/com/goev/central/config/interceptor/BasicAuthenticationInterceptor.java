package com.goev.central.config.interceptor;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dto.AuthClientDto;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.exceptions.ResponseException;
import com.goev.lib.services.RestClient;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
@AllArgsConstructor
public class BasicAuthenticationInterceptor implements HandlerInterceptor {
    private final RestClient restClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            throw new ResponseException("Invalid Access Token");
        }
        String tokenType = authorizationHeader.split(" ")[0];

        if (tokenType == null || !tokenType.toLowerCase().startsWith("basic"))
            throw new ResponseException("Invalid Access Token");
        String token = authorizationHeader.split(" ")[1];

        if (token == null)
            throw new ResponseException("Invalid Access Token");

        try {
            String url = ApplicationConstants.AUTH_URL + "/api/v1/client-management/clients";
            HttpHeaders header = new HttpHeaders();
            header.set("Authorization", RequestContext.getAccessToken());
            String responseStr = restClient.get(url, header, String.class, true);
            ResponseDto<AuthClientDto> client = ApplicationConstants.GSON.fromJson(responseStr, new TypeToken<ResponseDto<AuthClientDto>>() {
            }.getType());
            AuthClientDto authClientDto = client.getData();
            if (authClientDto == null || authClientDto.getUuid() == null) {
                throw new ResponseException(401, "Token Expired");
            }
            request.setAttribute("externalClientUUID", authClientDto.getUuid());
            request.setAttribute("externalClientId", authClientDto.getClientKey());
            request.setAttribute("organizationUUID", authClientDto.getOrganizationUUID());

        } catch (Exception e) {
            log.error("Error in checking token :", e);
            throw new ResponseException(401, "Invalid Token");
        }
        /** Code to Authenticate */


        return true;
    }

}
