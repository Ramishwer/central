package com.goev.central.config.interceptor;

import com.goev.central.dto.auth.AuthClientDto;
import com.goev.central.service.auth.AuthService;
import com.goev.lib.exceptions.ResponseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
@AllArgsConstructor
public class BasicAuthenticationInterceptor implements HandlerInterceptor {
   private final AuthService authService;

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

            AuthClientDto authClientDto = authService.getClient(authorizationHeader);
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
