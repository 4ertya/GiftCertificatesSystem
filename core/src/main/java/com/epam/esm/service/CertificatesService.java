package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;

import java.util.List;

public interface CertificatesService {

    List<CertificateDTO> readAll(String tagName, String partName, String partDescription, String dateSort, String nameSort);

    CertificateDTO read(long id);

    CertificateDTO create(CertificateDTO certificateDTO);

    CertificateDTO update(long id, CertificateDTO certificateDTO);

    CertificateDTO delete(long id);
}
