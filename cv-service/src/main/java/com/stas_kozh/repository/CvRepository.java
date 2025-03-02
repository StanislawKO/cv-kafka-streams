package com.stas_kozh.repository;

import com.stas_kozh.model.CvDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CvRepository extends JpaRepository<CvDocument, Long> {
    Optional<CvDocument> findByEmployeeId(Long employeeId);
}
