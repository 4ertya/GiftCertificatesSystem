package com.epam.esm.repository;


import com.epam.esm.model.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDAO {

    List<Certificate> readAll();

    Optional<Certificate> read(int id);

    Optional<Certificate> create(Certificate certificate);

    Optional<Certificate> update(Certificate certificate);

    Optional<Certificate> delete(int id);

}
