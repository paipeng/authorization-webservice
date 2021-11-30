package com.paipeng.authorization.service;

import com.paipeng.authorization.config.ApplicationConfig;
import com.paipeng.authorization.entity.User;
import com.paipeng.authorization.security.CustomUserDetails;
import com.paipeng.authorization.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    //@Autowired
    //private UserRepository userRepo;

    private ApplicationConfig applicationConfig;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserUtil.getUser(applicationConfig.getSecurityJwtSecret()); //userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

}
