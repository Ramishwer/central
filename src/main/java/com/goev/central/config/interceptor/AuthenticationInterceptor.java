package com.goev.central.config.interceptor;


import com.goev.central.dao.user.detail.UserSessionDao;
import com.goev.central.dto.session.SessionDetailsDto;
import com.goev.central.repository.user.detail.UserSessionRepository;
import com.goev.central.service.auth.AuthService;
import com.goev.central.utilities.RequestContext;
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
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final UserSessionRepository userSessionRepository;
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");

        if (token == null) {
            throw new ResponseException(401, "Invalid Access Token");
        }
        /** Code to Authenticate */


        UserSessionDao userSessionDao = userSessionRepository.findByUUID(RequestContext.getSessionUUID());

        if (userSessionDao == null)
            throw new ResponseException(401, "Invalid Session UUID");

        try {

            SessionDetailsDto sessionDto = authService.getSessionDetails(userSessionDao);
            if (sessionDto == null || sessionDto.getDetails() == null) {
                throw new ResponseException(401, "Token Expired");
            }
            request.setAttribute("authSessionUUID", sessionDto.getDetails().getUuid());
            request.setAttribute("authUUID", sessionDto.getDetails().getAuthUUID());
            request.setAttribute("organizationUUID", sessionDto.getDetails().getOrganizationUUID());
            request.setAttribute("userSession", userSessionDao);
        } catch (Exception e) {
            log.error("Error in checking token :", e);
            throw new ResponseException(401, "Invalid Token");
        }


        return true;
    }

}
