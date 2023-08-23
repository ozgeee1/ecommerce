package com.ecommerce.productmanagement.filter;

import com.ecommerce.productmanagement.config.KeycloakRoleConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakTokenFilter extends OncePerRequestFilter {


    private static final String BEARER = "Bearer ";
    private final static String authorizationHeader = "Authorization";

    private final JwtDecoder decoder;

    private final KeycloakRoleConverter roleConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(authorizationHeader);
        if(token!= null && token.contains("Bearer ")){
            try {
                if (token.length() > BEARER.length() && token.startsWith(BEARER)) {
                    token = token.substring(BEARER.length());
                    Jwt jwt = decoder.decode(token);
                    String username = jwt.getClaimAsString("preferred_username");
                    boolean isEmailVerified = Boolean.parseBoolean(jwt.getClaimAsString("email_verified"));
                    if(!isEmailVerified){
                        filterChain.doFilter(request,response);
                    }
                    Collection<GrantedAuthority> authorities = roleConverter.convert(jwt);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (Exception e){
                log.error(e.getMessage());
            }
        }
        filterChain.doFilter(request,response);

    }
}
