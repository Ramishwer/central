package com.goev.central.utilities;

import com.goev.central.dao.user.detail.UserSessionDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContext {
    private RequestContext() {
    }

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

    public static String getSessionUUID() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String sessionUUID = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getHeader("Session-UUID") != null)
                sessionUUID = request.getHeader("Session-UUID");
        }
        return sessionUUID;
    }


    public static String getPlatform() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String platform = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getHeader("Platform") != null)
                platform = request.getHeader("Platform");
        }
        return platform;
    }

    public static String getVersion() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String version = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getHeader("Version") != null)
                version = request.getHeader("Version");
        }
        return version;
    }

    public static String getLanguage() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String language = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getHeader("Language") != null)
                language = request.getHeader("Language");
        }
        return language;
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

    public static UserSessionDao getUserSession() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        UserSessionDao userSessionDao = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getAttribute("userSession") != null)
                userSessionDao = (UserSessionDao) request.getAttribute("userSession");
        }
        return userSessionDao;
    }

    public static String getRequestSource() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        String source = null;
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            if (request.getAttribute("requestSource") != null)
                source = (String) request.getAttribute("requestSource");
        }
        return source;
    }

    public static void setRequestSource(String source) {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            request.setAttribute("requestSource", source);
        }
    }


}
