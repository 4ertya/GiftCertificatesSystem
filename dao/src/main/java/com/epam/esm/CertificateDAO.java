package com.epam.esm;

import java.util.List;

public interface CertificateDAO {

    List<Certificate> readAll();
    Certificate read(int id);
    Certificate create(Certificate certificate);

}
