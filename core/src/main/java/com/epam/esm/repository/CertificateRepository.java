package com.epam.esm.repository;


import com.epam.esm.model.Certificate;
import com.epam.esm.repository.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository {

    List<Certificate> findAllCertificates();

    List<Certificate> findAllCertificatesBySpecification(Specification specification);

    Optional<Certificate> findCertificateById(long id);

    Certificate createCertificate(Certificate certificate);

    void updateCertificate(Certificate certificate);

    void deleteCertificate(long id);
}
