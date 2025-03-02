package com.stas_kozh.dto;

import java.time.LocalDateTime;

public record OutboxDto(
        String id,
        LocalDateTime createdAt,
        String payload,
        OutboxType type
) {}
