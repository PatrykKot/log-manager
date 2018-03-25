package com.kotlarz.backend.repository;

import com.kotlarz.backend.domain.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
