package com.paipeng.authorization.service;

import com.paipeng.authorization.config.ApplicationConfig;
import com.paipeng.authorization.entity.Authorization;
import com.paipeng.authorization.entity.Product;
import com.paipeng.authorization.entity.User;
import com.paipeng.authorization.repository.AuthorizationRepository;
import com.paipeng.authorization.repository.ProductRepository;
import com.paipeng.authorization.util.CommonUtil;
import com.paipeng.authorization.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService extends BaseService{
    private final static Logger logger = LogManager.getLogger(ProductService.class.getSimpleName());
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ImageService imageService;

    public Authorization save(Authorization authorization) throws Exception {
        User currentUser = getUserFromSecurity();
        if (currentUser == null) {
            throw new Exception("403");
        }

        if (productRepository.findById(authorization.getProduct().getId()).orElse(null) == null) {
            throw new Exception("404");
        }
        String filePath = imageService.saveImage(authorization.getImageBase64());
        authorization.setFilePath(filePath);
        authorization = authorizationRepository.saveAndFlush(authorization);
        return authorization;
    }

    public List<Authorization> getAllByProductId(Long id) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new Exception("404");
        }
        return authorizationRepository.findAllByProduct(product);
    }
}
