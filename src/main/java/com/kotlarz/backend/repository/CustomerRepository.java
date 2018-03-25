package com.kotlarz.backend.repository;

import com.kotlarz.backend.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
