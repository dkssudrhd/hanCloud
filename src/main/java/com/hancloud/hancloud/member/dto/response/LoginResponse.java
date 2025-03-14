package com.hancloud.hancloud.member.dto.response;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Builder;

@Builder
public record LoginResponse(
	String id,
	String password,
	String memberId,
	String email,
	String name,
	int count,
	List<SimpleGrantedAuthority> role
) {
}
