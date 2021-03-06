package com.paipeng.authorization.service;


import com.paipeng.authorization.config.ApplicationConfig;
import com.paipeng.authorization.entity.User;
import com.paipeng.authorization.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService {
    private final static Logger logger = LogManager.getLogger(UserService.class.getSimpleName());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationConfig applicationConfig;

    public User login(User user) throws Exception {
        logger.info("my login: " + user.getEmail());
        logger.info("my password: " + user.getPassword());

        if (user.getPassword() != null) {
            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //String encodedPassword = passwordEncoder.encode(user.getPassword());
            //logger.trace("encodedPassword: " + encodedPassword);
            User foundUser = userRepository.findByEmail(user.getEmail());

            if (foundUser != null) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                    String token = getJWTToken(user.getEmail());
                    logger.info("token: " + token.length());
                    foundUser.setToken(token);
                    foundUser = userRepository.saveAndFlush(foundUser);
                    return foundUser;
                } else {
                    throw new Exception("401");
                }
            } else {
                throw new Exception("401");
            }
        }
        throw new Exception("401");
    }

    @SuppressWarnings("undeprecated")
    private String getJWTToken(String username) {
        //SecureRandom random = new SecureRandom();
        //byte[] bytes = new byte[64]; // 36 bytes * 8 = 288 bits, a little bit more than
        // the 256 required bits
        //random.nextBytes(bytes);

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        return Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        applicationConfig.getSecurityJwtSecret()).compact();
    }

    public void logout() {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
                logger.info("auth: " + grantedAuthority.getAuthority());
                logger.info("auth: " + grantedAuthority);
            }
            User user = (User) auth.getDetails();
            if (user != null) {
                logger.info("loginUser: " + user.getEmail());
                user.setToken(null);
                userRepository.saveAndFlush(user);
            }
        }
    }

    public User register(User user) throws Exception {
        logger.info("register: " + user.getEmail());
        logger.info("my password: " + user.getPassword());

        if (user.getEmail() != null && user.getPassword() != null) {
            User foundUser = userRepository.findByEmail(user.getEmail());
            if (foundUser != null) {
                throw new Exception("409");
            } else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                logger.trace("encodedPassword: " + encodedPassword);
                user.setPassword(encodedPassword);
                user = userRepository.saveAndFlush(user);
                return user;
            }
        } else {
            throw new Exception("400");
        }
    }
}
