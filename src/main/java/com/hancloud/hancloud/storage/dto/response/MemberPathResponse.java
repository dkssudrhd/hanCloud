package com.hancloud.hancloud.storage.dto.response;

import lombok.Builder;

@Builder
public record MemberPathResponse(
	boolean nowLogin,
	String auth) {
}
