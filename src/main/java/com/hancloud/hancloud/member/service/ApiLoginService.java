package com.hancloud.hancloud.member.service;

import com.hancloud.hancloud.member.dto.request.LoginRequest;
import com.hancloud.hancloud.member.dto.response.LoginResponse;

public interface ApiLoginService {
    LoginResponse login(String id);
    LoginResponse loginChecking(LoginRequest loginRequest);
}
