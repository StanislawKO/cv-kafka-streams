package com.stas_kozh.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cv_documents")
public class CvDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "education")
    private String education;

    @Column(name = "description")
    private String description;

    @Column(name = "work_experience")
    private String workExperience;

    @Column(name = "is_open_to_work")
    private boolean isOpenToWork;

    @Column(name = "linked_id")
    private String linkedId;

    @Column(name = "employee_id")
    private Long employeeId;

    @OneToMany(mappedBy = "cvDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "cvDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Language> languages;

    @OneToMany(mappedBy = "cvDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;
}
