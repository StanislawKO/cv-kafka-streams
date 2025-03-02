package com.stas_kozh.mapper;

import com.stas_kozh.dto.CreateEmployeeDto;
import com.stas_kozh.dto.CvDto;
import com.stas_kozh.dto.EmployeeDto;

import com.stas_kozh.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    Employee toEntity(CreateEmployeeDto employeeDto);

    EmployeeDto toDto(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeId", source = "employeeId")
    CvDto toCvDto(CreateEmployeeDto employeeDto, Long employeeId);
}
