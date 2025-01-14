package com.hancloud.hancloud.security.service.impl;

import com.hancloud.hancloud.member.dto.response.LoginResponse;
import com.hancloud.hancloud.member.service.ApiLoginService;
import com.hancloud.hancloud.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final ApiLoginService apiLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginResponse response = apiLoginService.login(username);

        return new CustomUserDetails(response);
    }
}
