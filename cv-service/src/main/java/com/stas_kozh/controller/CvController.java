package com.stas_kozh.controller;

import com.stas_kozh.dto.CvDto;
import com.stas_kozh.service.CvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cv")
@RequiredArgsConstructor
@Slf4j
public class CvController {
    private final CvService cvService;

    @GetMapping("/{id}")
    public ResponseEntity<CvDto> getCv(@PathVariable Long id) {
        log.info("Get cv {}", id);
        return ResponseEntity.ok(cvService.getCv(id));
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<CvDto> getCvByEmployeeId(@PathVariable Long id) {
        log.info("Get cv by employee id {}", id);
        return ResponseEntity.ok(cvService.getCvByEmployeeId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CvDto> createCvDocument(@RequestBody CvDto request) {
        return ResponseEntity.ok(cvService.create(request));
    }
}
