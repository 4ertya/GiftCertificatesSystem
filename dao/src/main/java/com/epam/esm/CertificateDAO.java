package com.epam.esm;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateDAO {

    List<Certificate> readAll();

    Certificate read(int id);

    Certificate create(Certificate certificate);

    Certificate update(Certificate certificate);

}
