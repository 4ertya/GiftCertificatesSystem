package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {

    List<CertificateDTO> findAllCertificates(String tagName, String partName, String partDescription, String dateSort, String nameSort);

    CertificateDTO findCertificateById(long id);

    CertificateDTO createCertificate(CertificateDTO certificateDTO);

    CertificateDTO updateCertificate(CertificateDTO certificateDTO);

    void deleteCertificate(long id);
}
