package com.paipeng.authorization.util;

import com.paipeng.authorization.entity.User;

public class UserUtil {

    public static User getUser(String token) {
        User user = new User();
        user.setEmail("sipaipv6@gmail.com");
        user.setFirstName("Pai");
        user.setLastName("Peng");
        user.setToken(token);
        return user;
    }
}
