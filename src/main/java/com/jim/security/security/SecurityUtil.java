package com.jim.security.security;

import com.jim.security.dao.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static boolean isAuthenticatedUser(long userId){
        var principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUserId() == userId;
    }

    public static  boolean hasRole(String role){
        var principal = (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.hasRole(role);
    }
}
