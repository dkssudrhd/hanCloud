package com.hancloud.hancloud.util.exceptionhandler;

import lombok.Builder;

@Builder
public record ErrorResponseForm(String title, int status, String timestamp) {
}
