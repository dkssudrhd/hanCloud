package com.hancloud.hancloud.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hancloud.hancloud.member.dto.request.LoginRequest;
import com.hancloud.hancloud.member.dto.response.LoginResponse;
import com.hancloud.hancloud.member.service.ApiLoginService;
import com.hancloud.hancloud.storage.dto.response.MemberPathResponse;
import com.hancloud.hancloud.storage.service.StorageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 */
@RequiredArgsConstructor
public class ApiAuthenticationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final ApiLoginService logInServiceApi;
    private final StorageService storageService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String apiId = request.getHeader("API-ID");
        String apiPassword = request.getHeader("API-PASSWORD");
        Authentication auth;

        String path = request.getParameter("path");

        if(Objects.nonNull(apiId)){
            // 회원
            LoginResponse loginResponse = logInServiceApi.loginChecking(LoginRequest.builder()
                    .id(apiId)
                    .password(apiPassword).build());
            MemberPathResponse memberPathResponse = storageService.canUse(loginResponse.memberId(), path);

            auth = new UsernamePasswordAuthenticationToken(loginResponse.memberId(), null,
                    List.of(new SimpleGrantedAuthority(memberPathResponse.auth())));
        } else {
            // 비회원
            MemberPathResponse memberPathResponse = storageService.canUse(null, path);

            auth = new UsernamePasswordAuthenticationToken(null, null,
                    List.of(new SimpleGrantedAuthority(memberPathResponse.auth())));
        }
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
