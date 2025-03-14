package com.hancloud.hancloud.member.dto.request;

import lombok.Builder;

@Builder
public record LoginRequest(
	String id,
	String password) {
}
