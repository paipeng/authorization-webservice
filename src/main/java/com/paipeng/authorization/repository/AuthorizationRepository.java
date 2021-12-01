package com.paipeng.authorization.repository;

import com.paipeng.authorization.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
}
