package com.hancloud.hancloud.security.filter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hancloud.hancloud.member.dto.request.LoginRequest;
import com.hancloud.hancloud.member.dto.response.LoginResponse;
import com.hancloud.hancloud.member.service.ApiLoginService;
import com.hancloud.hancloud.storage.dto.response.MemberPathResponse;
import com.hancloud.hancloud.storage.service.StorageService;
import com.hancloud.hancloud.util.ApiResponse;
import com.hancloud.hancloud.util.exceptionhandler.ErrorResponseForm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 *
 */
@Slf4j
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
		try {
			String apiId = request.getHeader("API-ID");
			String apiPassword = request.getHeader("API-PASSWORD");
			Authentication auth;
			String path = request.getParameter("path");

			if (!Objects.isNull(path)) {
				if (Objects.nonNull(apiId)) {
					// 회원 로그인 검증
					LoginResponse loginResponse = logInServiceApi.loginChecking(LoginRequest.builder()
						.id(apiId)
						.password(apiPassword).build());
					MemberPathResponse memberPathResponse = storageService.canUse(loginResponse.memberId(), path);

					auth = new UsernamePasswordAuthenticationToken(loginResponse.memberId(), null,
						List.of(new SimpleGrantedAuthority(memberPathResponse.auth())));
				} else {
					// 비회원 검증
					MemberPathResponse memberPathResponse = storageService.canUse(null, path);

					auth = new UsernamePasswordAuthenticationToken(null, null,
						List.of(new SimpleGrantedAuthority(memberPathResponse.auth())));
				}
			} else {
				filterChain.doFilter(request, response);
				return;
			}

			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);

		} catch (Exception e) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(objectMapper.writeValueAsString(
				ApiResponse.badRequestFail(
					ErrorResponseForm.builder()
						.title(e.getMessage())
						.status(HttpStatus.UNAUTHORIZED.value())
						.timestamp(ZonedDateTime.now().toString())
						.build()
				)
			));
		}
	}
}
