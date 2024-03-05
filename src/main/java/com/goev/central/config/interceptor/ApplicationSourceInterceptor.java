package com.goev.central.config.interceptor;


import com.goev.central.config.SpringContext;
import com.goev.central.constant.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class ApplicationSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        ApplicationConstants applicationConstants = SpringContext.getBean(ApplicationConstants.class);
        request.setAttribute("applicationSource", applicationConstants.APPLICATION_ID);
        request.setAttribute("applicationClientId", applicationConstants.CLIENT_ID);
        request.setAttribute("applicationClientSecret", applicationConstants.CLIENT_SECRET);
        request.setAttribute("requestUUID", UUID.randomUUID().toString());
        request.setAttribute("applicationUsername", applicationConstants.USER_NAME);
        request.setAttribute("applicationPassword", applicationConstants.USER_PASSWORD);
        return true;
    }
}