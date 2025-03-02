package com.stas_kozh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stas_kozh.client.CvServiceClient;
import com.stas_kozh.dto.CreateEmployeeDto;
import com.stas_kozh.dto.CvDto;
import com.stas_kozh.dto.EmployeeDto;
import com.stas_kozh.dto.OutboxType;
import com.stas_kozh.mapper.EmployeeMapper;
import com.stas_kozh.model.Employee;
import com.stas_kozh.model.Outbox;
import com.stas_kozh.repository.EmployeeRepository;
import com.stas_kozh.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CvServiceClient cvClient;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmployeeDto getById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public CvDto getEmployeeCv(String id) {
        return cvClient.getCvByEmployeeId(id);
    }

    @SneakyThrows
    @Transactional
    public EmployeeDto create(CreateEmployeeDto employeeDto) {
        Employee createdEmployee = employeeRepository.save(
                employeeMapper.toEntity(employeeDto));

        CvDto cvDto = employeeMapper.toCvDto(employeeDto, createdEmployee.getId());
        Outbox outboxRecord = new Outbox();
        outboxRecord.setPayload(objectMapper.writeValueAsString(cvDto));
        outboxRecord.setType(OutboxType.CV.name());
        outboxRepository.save(outboxRecord);

        return employeeMapper.toDto(createdEmployee);
    }
}
