package com.stas_kozh.dto;

import java.util.List;

public record CvDto(
        String id,
        String education,
        String description,
        String workExperience,
        List<String> skills,
        List<String> languages,
        List<String> certificates,
        String linkedId,
        boolean isOpenToWork,
        Long employeeId
) {}
