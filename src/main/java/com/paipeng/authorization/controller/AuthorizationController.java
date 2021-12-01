package com.paipeng.authorization.controller;

import com.paipeng.authorization.entity.Authorization;
import com.paipeng.authorization.entity.Product;
import com.paipeng.authorization.service.AuthorizationService;
import com.paipeng.authorization.service.ProductService;
import com.sun.istack.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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


    @GetMapping(value = "/products/{id}", produces = {"application/json;charset=UTF-8"})
    public List<Authorization> getAll(@NotNull @PathVariable("id") Long id, HttpServletResponse httpServletResponse) throws Exception {
        logger.info("getAllByProductId: " + id);
        return authorizationService.getAllByProductId(id);
    }
}
