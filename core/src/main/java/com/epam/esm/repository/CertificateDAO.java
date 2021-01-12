package com.epam.esm.repository;


import com.epam.esm.model.Certificate;

import java.util.List;

public interface CertificateDAO {

    List<Certificate> readAll();

    Certificate read(int id);

    Certificate create(Certificate certificate);

    Certificate update(Certificate certificate);

}
