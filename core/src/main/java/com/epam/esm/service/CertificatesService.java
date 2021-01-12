package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.model.Certificate;

import java.util.List;

public interface CertificatesService {

    List<CertificateDTO> readAll();

    CertificateDTO read(int id);

    Certificate create(Certificate certificate);

    Certificate update(int id, Certificate certificate);
}
