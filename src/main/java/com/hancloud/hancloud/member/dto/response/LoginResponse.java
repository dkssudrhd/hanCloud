package com.hancloud.hancloud.member.dto.response;

import lombok.Builder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

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