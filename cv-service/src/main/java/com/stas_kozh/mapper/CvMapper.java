package com.stas_kozh.mapper;

import com.stas_kozh.dto.CvDto;
import com.stas_kozh.model.Certificate;
import com.stas_kozh.model.CvDocument;
import com.stas_kozh.model.Language;
import com.stas_kozh.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CvMapper {

    @Mapping(target = "skills", source = "skills", qualifiedByName = "mapSkillsDto")
    @Mapping(target = "languages", source = "languages", qualifiedByName = "mapLanguagesDto")
    @Mapping(target = "certificates", source = "certificates", qualifiedByName = "mapCertificatesDto")
    CvDto toCvDto(CvDocument cvDocument);

    @Named("mapSkillsDto")
    default List<String> mapSkillsDto(List<Skill> skills) {
        return skills.stream().map(Skill::getSkillName).toList();
    }

    @Named("mapLanguagesDto")
    default List<String> mapLanguagesDto(List<Language> languages) {
        return languages.stream().map(Language::getLanguageName).toList();
    }

    @Named("mapCertificatesDto")
    default List<String> mapCertificatesDto(List<Certificate> certificates) {
        return certificates.stream().map(Certificate::getCertificateName).toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "skills", source = "skills", qualifiedByName = "mapSkills")
    @Mapping(target = "languages", source = "languages", qualifiedByName = "mapLanguages")
    @Mapping(target = "certificates", source = "certificates", qualifiedByName = "mapCertificates")
    CvDocument toCvDocument(CvDto cvDto);

    @Named("mapSkills")
    default List<Skill> mapSkills(List<String> skills) {
        return skills.stream().map(skill -> {
            Skill newSkill = new Skill();
            newSkill.setSkillName(skill);
            return newSkill;
        }).toList();
    }

    @Named("mapLanguages")
    default List<Language> mapLanguages(List<String> languages) {
        return languages.stream().map(lang -> {
            Language newLanguage = new Language();
            newLanguage.setLanguageName(lang);
            return newLanguage;
        }).toList();
    }

    @Named("mapCertificates")
    default List<Certificate> mapCertificates(List<String> certificates) {
        return certificates.stream().map(cert -> {
            Certificate newCert = new Certificate();
            newCert.setCertificateName(cert);
            return newCert;
        }).toList();
    }
}
