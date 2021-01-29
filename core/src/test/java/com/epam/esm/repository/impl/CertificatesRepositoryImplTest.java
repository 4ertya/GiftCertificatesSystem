package com.epam.esm.repository.impl;

import com.epam.esm.config.EmbeddedTestConfig;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringJUnitConfig(EmbeddedTestConfig.class)
class CertificatesRepositoryImplTest {

    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private TagRepository tagRepository;

    @Test
    void findAllCertificates() {
        Certificate certificate1 = new Certificate("Certificate 1", "Description 1", BigDecimal.valueOf(100), 10);
        certificate1.setId(1L);
        certificate1.setCreateDate(LocalDateTime.of(2021, 1, 1, 18, 0, 0, 0));
        certificate1.setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 18, 0, 0, 0));
        Certificate certificate2 = new Certificate("Certificate 2", "Description 2", BigDecimal.valueOf(200), 20);
        certificate2.setId(2L);
        certificate2.setCreateDate(LocalDateTime.of(2021, 1, 10, 18, 0, 0, 0));
        certificate2.setLastUpdateDate(LocalDateTime.of(2021, 1, 10, 18, 0, 0, 0));
        Certificate certificate3 = new Certificate("Certificate 3", "Description 3", BigDecimal.valueOf(300), 30);
        certificate3.setId(3L);
        certificate3.setCreateDate(LocalDateTime.of(2021, 1, 20, 18, 0, 0, 0));
        certificate3.setLastUpdateDate(LocalDateTime.of(2021, 1, 20, 18, 0, 0, 0));
        List<Certificate> expected = Arrays.asList(certificate1,certificate2,certificate3);
        List<Certificate> actual = certificateRepository.findAllCertificates();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void findAllCertificatesBySpecification() {
    }

    @Test
    void findCertificateById() {
        Certificate expected = new Certificate("Certificate 1", "Description 1", BigDecimal.valueOf(100), 10);
        expected.setId(1L);
        expected.setCreateDate(LocalDateTime.of(2021, 1, 1, 18, 0, 0, 0));
        expected.setLastUpdateDate(LocalDateTime.of(2021, 1, 1, 18, 0, 0, 0));
        Certificate actual = certificateRepository.findCertificateById(1).get();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createCertificate() {
        Certificate expected = new Certificate("new", "new", BigDecimal.valueOf(12.2), 10);
        expected.setId(4L);
        Certificate actual = certificateRepository.createCertificate(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateCertificate() {
        Certificate expected = new Certificate("updated", "updated", BigDecimal.valueOf(12.2), 10);
        expected.setId(1L);
        expected.setCreateDate(LocalDateTime.of(2021, 1, 1, 18, 0, 0, 0));
        certificateRepository.updateCertificate(expected);
        Certificate actual = certificateRepository.findCertificateById(1).get();
        expected.setLastUpdateDate(actual.getLastUpdateDate());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteCertificate() {
        tagRepository.unbindByCertificateId(1);
        certificateRepository.deleteCertificate(1);
        Assertions.assertEquals(Optional.empty(), certificateRepository.findCertificateById(1));
    }
}