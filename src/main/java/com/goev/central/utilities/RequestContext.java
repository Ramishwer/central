package com.goev.central.utilities;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContext {
    public static String getAccessToken() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String clientSecret = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getHeader("Authorization") != null)
                clientSecret = request.getHeader("Authorization");
        }
        return clientSecret;
    }

    public static String getRefreshToken() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String clientSecret = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getHeader("Refresh-Token") != null)
                clientSecret = request.getHeader("Refresh-Token");
        }
        return clientSecret;
    }
}
