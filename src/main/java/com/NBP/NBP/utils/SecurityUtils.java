package com.NBP.NBP.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.NBP.NBP.security.CustomUserDetails;

public class SecurityUtils {
    public static int getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUserId();
    }
}
