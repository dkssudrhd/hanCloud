package com.hancloud.hancloud.security.utils;

import com.hancloud.hancloud.storage.dto.enums.StorageAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityUtils {

    public static String getCurrentMemberId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static List<? extends GrantedAuthority> getCurrentAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().toList();
    }
}
