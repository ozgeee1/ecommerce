package com.ecommerce.usermanagement.config;

import com.ecommerce.usermanagement.filter.KeycloakTokenFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.OutputStream;

@Configuration
@KeycloakConfiguration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class KeycloakConfig  {

    private final KeycloakTokenFilter tokenFilter;

    public AuthenticationEntryPoint authenticationEntryPoint(){
        return ((request, response, authException) -> {
            System.out.println("AuthenticationEntryPoint -> AuthenticationException occured!");
            //int SC_UNAUTHORIZED=401
            //int SC_FORBIDDEN = 403
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            OutputStream out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.writeValue(out,"Bu sayfaya erişmeye yetkiniz yoktur");
            out.flush();
        });
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Bean oluştu");

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint()))
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(e->e.requestMatchers("/user").permitAll())
                .authorizeHttpRequests(e -> e.requestMatchers("/user/**","/address/**").authenticated())

                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }



}
