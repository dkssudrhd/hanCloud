package com.hancloud.hancloud.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiMessage {
    boolean isSuccessful;
    String message;

    public static ApiMessage success(String message) {
        return new ApiMessage(true, message);
    }

    public static ApiMessage failure(String message) {
        return new ApiMessage(false, message);
    }
}
