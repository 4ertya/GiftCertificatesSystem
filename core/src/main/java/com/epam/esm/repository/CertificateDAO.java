package com.epam.esm.repository;


import com.epam.esm.model.Certificate;
import com.epam.esm.repository.specification.Specification;

import java.util.List;
import java.util.Optional;

public interface CertificateDAO {

    List<Certificate> readAll();

    List<Certificate> readAllBySpecification(Specification specification);

    Optional<Certificate> read(long id);

    Optional<Certificate> create(Certificate certificate);

    Optional<Certificate> update(Certificate certificate);

    Optional<Certificate> delete(long id);

}
