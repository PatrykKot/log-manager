package com.kotlarz.backend.repository.logs;

import com.kotlarz.backend.domain.logs.FormatterConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormatterConfigRepository extends JpaRepository<FormatterConfigEntity, Long> {
    FormatterConfigEntity findByCustomer_Id(Long id);
}
