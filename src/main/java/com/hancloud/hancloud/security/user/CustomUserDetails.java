package com.hancloud.hancloud.security.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hancloud.hancloud.member.dto.response.LoginResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final String id;
	private final String password;
	private final String memberId;
	private final String email;
	private final String name;
	private final int count;
	private final Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(LoginResponse loginResponse) {
		this.id = loginResponse.id();
		this.password = loginResponse.password();
		this.memberId = loginResponse.memberId();
		this.email = loginResponse.email();
		this.name = loginResponse.name();
		this.count = loginResponse.count();
		this.authorities = loginResponse.role();
	}

	@Override
	public String getUsername() {
		return memberId;
	}
}
