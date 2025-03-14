package com.hancloud.hancloud.storage.dto.response;

import com.hancloud.hancloud.storage.dto.enums.StorageAuth;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberPathResponse(
        boolean nowLogin,
        String auth) {
}
