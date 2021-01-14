package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.model.Certificate;

import java.util.List;

public interface CertificatesService {

    List<CertificateDTO> readAll();

    CertificateDTO read(int id);

    CertificateDTO create(CertificateDTO certificateDTO);

    CertificateDTO update(int id, CertificateDTO certificateDTO);

    CertificateDTO delete(int id);
}
