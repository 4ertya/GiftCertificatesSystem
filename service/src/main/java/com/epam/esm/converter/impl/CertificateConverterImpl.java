package com.epam.esm.converter.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CertificateConverterImpl implements CertificateConverter {

    @Override
    public Certificate toEntity(CertificateDTO certificateDTO) {
        Certificate certificate;
        certificate = new Certificate(
                certificateDTO.getName(),
                certificateDTO.getDescription(),
                certificateDTO.getPrice(),
                certificateDTO.getDuration()
        );
        return certificate;
    }

    @Override
    public CertificateDTO toDto(Certificate certificate, List<Tag> tags) {
        CertificateDTO certificateDTO;
        certificateDTO = new CertificateDTO(
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                tags
        );
        certificateDTO.setId(certificate.getId());
        certificateDTO.setCreateDate(certificate.getCreateDate());
        certificateDTO.setLastUpdateDate(certificate.getLastUpdateDate());
        return certificateDTO;
    }
}

