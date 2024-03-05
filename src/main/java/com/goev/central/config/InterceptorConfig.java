package com.goev.central.config;


import com.goev.central.config.interceptor.ApplicationSourceInterceptor;
import com.goev.central.config.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    private ApplicationSourceInterceptor applicationSourceInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(applicationSourceInterceptor);
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**").
                excludePathPatterns(
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/api/v1/status",
                        "/api/v1/session-management/session"
                );
    }
}