package com.paipeng.authorization.service;

import com.paipeng.authorization.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseService {
    protected User getUserFromSecurity() {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return  (User) auth.getDetails();
        } else {
            return null;
        }
    }
}
