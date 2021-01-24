package com.epam.esm.repository;


import com.epam.esm.model.Certificate;
import com.epam.esm.repository.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository {

    List<Certificate> readAll();

    List<Certificate> readAllBySpecification(Specification specification);

    Optional<Certificate> read(long id);

    Certificate create(Certificate certificate);

    void update(Certificate certificate);

    void delete(long id);
}
