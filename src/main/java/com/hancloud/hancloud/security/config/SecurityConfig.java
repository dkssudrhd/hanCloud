package com.hancloud.hancloud.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hancloud.hancloud.member.service.ApiLoginService;
import com.hancloud.hancloud.security.filter.ApiAuthenticationFilter;
import com.hancloud.hancloud.security.filter.AuthenticationProcessingFilterCustom;
import com.hancloud.hancloud.security.handler.AuthenticationSuccessHandlerImpl;
import com.hancloud.hancloud.storage.service.StorageService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final ObjectMapper objectMapper;
	private final ApiLoginService apiLoginService;
	private final StorageService storageService;

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public ApiAuthenticationFilter authenticationFilterCustom() {
		return new ApiAuthenticationFilter(objectMapper, apiLoginService, storageService);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
		Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.addFilterBefore(authenticationFilterCustom(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * Register AuthenticationSuccessHandler bean.
	 *
	 * @return AuthenticationSuccessHandler
	 */
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandlerImpl();
	}

	@Bean
	public AbstractAuthenticationProcessingFilter authenticationProcessingFilter(
		AuthenticationManager authenticationManager) {
		AuthenticationProcessingFilterCustom loginFilter =
			new AuthenticationProcessingFilterCustom("/*", authenticationManager);

		loginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());

		return loginFilter;
	}

}
