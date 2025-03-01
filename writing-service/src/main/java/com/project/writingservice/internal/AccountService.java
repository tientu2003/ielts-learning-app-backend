package com.project.writingservice.internal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class AccountService {
    private final Object principal = SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Map<String, Object> claims = ((Jwt) principal).getClaims();

    public String getUserId(){
        return claims.get("sub").toString();
    }
}
