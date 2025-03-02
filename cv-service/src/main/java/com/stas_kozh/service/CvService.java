package com.stas_kozh.service;

import com.stas_kozh.model.CvDocument;
import lombok.RequiredArgsConstructor;
import com.stas_kozh.dto.CvDto;
import com.stas_kozh.mapper.CvMapper;
import com.stas_kozh.repository.CvRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CvService {
    private final CvRepository cvRepository;
    private final CvMapper cvMapper;

    public CvDto getCv(Long id) {
        log.info("Get cv {}", id);
        return cvRepository.findById(id)
                .map(cvMapper::toCvDto)
                .orElseThrow();
    }

    public CvDto getCvByEmployeeId(Long id) {
        return cvRepository.findByEmployeeId(id)
                .map(cvMapper::toCvDto)
                .orElseThrow(() -> new RuntimeException("Cv not found for employee: " + id));
    }

    @Transactional
    public CvDto create(CvDto cvDto) {
        CvDocument cvDocument = cvMapper.toCvDocument(cvDto);

        cvDocument.getSkills().forEach(skill -> skill.setCvDocument(cvDocument));
        cvDocument.getLanguages().forEach(lang -> lang.setCvDocument(cvDocument));
        cvDocument.getCertificates().forEach(cert -> cert.setCvDocument(cvDocument));

        return cvMapper.toCvDto(cvRepository.save(cvDocument));
    }
}
