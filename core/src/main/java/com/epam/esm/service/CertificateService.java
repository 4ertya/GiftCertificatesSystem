package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.DataSortType;

import java.util.List;

public interface CertificateService {

    List<CertificateDTO> findAllCertificates(String tagName, String partName, String partDescription, DataSortType dateSort, DataSortType nameSort);

    CertificateDTO findCertificateById(long id);

    CertificateDTO createCertificate(CertificateDTO certificateDTO);

    CertificateDTO updateCertificate(CertificateDTO certificateDTO);

    void deleteCertificate(long id);
}
