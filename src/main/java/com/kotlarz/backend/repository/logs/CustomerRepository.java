package com.kotlarz.backend.repository.logs;

import com.kotlarz.backend.domain.logs.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
