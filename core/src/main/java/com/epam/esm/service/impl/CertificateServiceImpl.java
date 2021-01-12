package com.epam.esm.service.impl;


import com.epam.esm.dto.CertificateDTO;

import com.epam.esm.mapper.CertificateConverter;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.CertificateDAO;
import com.epam.esm.repository.TagDAO;
import com.epam.esm.service.CertificatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertificateServiceImpl implements CertificatesService {

    private final CertificateDAO certificateDAO;
    private final TagDAO tagDAO;
    private final CertificateConverter converter;

    @Override
    public List<CertificateDTO> readAll() {
        List<Certificate> certificates = certificateDAO.readAll();
        List<CertificateDTO> certificateDTOS = new LinkedList<>();
        for (Certificate certificate : certificates) {
            List<Tag> tags = tagDAO.findByCertificateId(certificate.getId());
            certificateDTOS.add(converter.toDto(certificate, tags));
        }
        return certificateDTOS;
    }

    @Override
    public CertificateDTO read(int id) {
        Certificate certificate = certificateDAO.read(id);
        List<Tag> tags = tagDAO.findByCertificateId(id);
        return converter.toDto(certificate, tags);
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificateDAO.create(certificate);
        return null;
    }

    @Override
    public Certificate update(int id, Certificate certificate) {
        return null;
    }

    List<CertificateDTO> toCertificatesDto(List<Certificate> certificates) {
        List<CertificateDTO> certificatesDTO = new ArrayList<>();
        for (Certificate certificate : certificates) {
            List<Tag> tags = tagDAO.findByCertificateId(certificate.getId());
            certificatesDTO.add(converter.toDto(certificate, tags));
        }
        return certificatesDTO;
    }
}