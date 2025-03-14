package com.hancloud.hancloud.security.utils;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static String getCurrentMemberId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public static List<? extends GrantedAuthority> getCurrentAuthorities() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
			.stream().toList();
	}
}
