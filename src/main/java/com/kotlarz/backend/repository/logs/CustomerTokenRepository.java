package com.kotlarz.backend.repository.logs;

import com.kotlarz.backend.domain.logs.CustomerTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTokenRepository extends JpaRepository<CustomerTokenEntity, Long> {
    CustomerTokenEntity findByToken(String token);
}
