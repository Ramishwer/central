package com.goev.central.config;


import com.goev.central.config.interceptor.ApplicationSourceInterceptor;
import com.goev.central.config.interceptor.AuthenticationInterceptor;
import com.goev.central.config.interceptor.BasicAuthenticationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {


    private final AuthenticationInterceptor authenticationInterceptor;
    private final BasicAuthenticationInterceptor basicAuthenticationInterceptor;
    private final ApplicationSourceInterceptor applicationSourceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(applicationSourceInterceptor);
        registry.addInterceptor(basicAuthenticationInterceptor).addPathPatterns("/api/v1/internal/**");
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**").
                excludePathPatterns(
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/api/v1/status",
                        "/api/v1/session-management/session",
                        "/api/v1/session-management/sessions/**/token",
                        "/api/v1/session-management/sessions/tokens",
                        "/api/v1/internal/**"
                );
    }
}