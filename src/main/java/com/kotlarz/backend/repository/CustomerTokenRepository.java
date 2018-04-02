package com.kotlarz.backend.repository;

import com.kotlarz.backend.domain.CustomerTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTokenRepository extends JpaRepository<CustomerTokenEntity, Long> {
    CustomerTokenEntity findByToken(String token);
}
