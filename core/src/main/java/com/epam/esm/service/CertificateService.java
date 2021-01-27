package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.DataSortOrder;

import java.util.List;

public interface CertificateService {

    List<CertificateDTO> findAllCertificates(String tagName, String partName, String partDescription, DataSortOrder dateSort, DataSortOrder nameSort);

    CertificateDTO findCertificateById(long id);

    CertificateDTO createCertificate(CertificateDTO certificateDTO);

    CertificateDTO updateCertificate(CertificateDTO certificateDTO);

    void deleteCertificate(long id);
}
