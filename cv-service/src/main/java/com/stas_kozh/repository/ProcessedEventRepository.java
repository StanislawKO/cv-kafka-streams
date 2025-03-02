package com.stas_kozh.repository;

import com.stas_kozh.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
    Optional<ProcessedEvent> findByMessageId(String messageId);
}
