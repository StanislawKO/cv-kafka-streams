package com.stas_kozh.dto;

public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        Integer age
) {}
