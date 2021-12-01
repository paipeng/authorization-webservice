package com.paipeng.authorization.service;

import com.paipeng.authorization.config.ApplicationConfig;
import com.paipeng.authorization.entity.Product;
import com.paipeng.authorization.entity.User;
import com.paipeng.authorization.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends BaseService {
    private final static Logger logger = LogManager.getLogger(ProductService.class.getSimpleName());
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationConfig applicationConfig;

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product save(Product product) throws Exception {
        User currentUser = getUserFromSecurity();
        if (currentUser == null) {
            throw new Exception("403");
        }

        if (productRepository.findByBarcode(product.getBarcode()).orElse(null) != null) {
            throw new Exception("409");
        }

        product.setUser(currentUser);

        product = productRepository.saveAndFlush(product);
        return product;
    }

    public Optional<Product> getProductByBarcode(String barcodeData) {
        return productRepository.findByBarcode(barcodeData);
    }
}
