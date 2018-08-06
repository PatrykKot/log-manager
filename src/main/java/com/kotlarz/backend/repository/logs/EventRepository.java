package com.kotlarz.backend.repository.logs;

import com.kotlarz.backend.domain.logs.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
