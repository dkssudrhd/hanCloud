package com.hancloud.hancloud.security.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class AuthenticationProcessingFilterCustom
        extends AbstractAuthenticationProcessingFilter {

    public AuthenticationProcessingFilterCustom(String defaultFilterProcessesUrl,
                                                AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String id = request.getHeader("API ID");
        String password = request.getHeader("API PASSWORD");

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(id, password));
    }
}
