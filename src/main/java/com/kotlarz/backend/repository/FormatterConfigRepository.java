package com.kotlarz.backend.repository;

import com.kotlarz.backend.domain.FormatterConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormatterConfigRepository extends JpaRepository<FormatterConfigEntity, Long> {
    FormatterConfigEntity findByCustomer_Id(Long id);
}
