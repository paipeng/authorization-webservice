package com.paipeng.authorization.controller;

import com.paipeng.authorization.entity.Authorization;
import com.paipeng.authorization.entity.Product;
import com.paipeng.authorization.service.AuthorizationService;
import com.paipeng.authorization.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/authorizations")
public class AuthorizationController {
    private final static Logger logger = LogManager.getLogger(AuthorizationController.class.getSimpleName());
    @Autowired
    private AuthorizationService authorizationService;


    @PostMapping(value = "", produces = {"application/json;charset=UTF-8"})
    public Authorization save(@RequestBody Authorization authorization, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setStatus(HttpStatus.CREATED.value());
        return authorizationService.save(authorization);
    }
}
