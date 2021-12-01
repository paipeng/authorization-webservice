package com.paipeng.authorization.repository;

import com.paipeng.authorization.entity.Authorization;
import com.paipeng.authorization.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
    List<Authorization> findAllByProduct(Product product);
}
