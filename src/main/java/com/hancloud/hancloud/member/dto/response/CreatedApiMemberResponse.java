package com.hancloud.hancloud.member.dto.response;

import lombok.Builder;

@Builder
public record CreatedApiMemberResponse(
        String apiId,
        String apiPassword
) {
}
