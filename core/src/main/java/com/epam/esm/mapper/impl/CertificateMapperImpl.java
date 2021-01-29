package com.epam.esm.mapper.impl;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.Certificate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CertificateMapperImpl implements CertificateMapper {

    @Override
    public Certificate toEntity(CertificateDTO certificateDTO) {
        if (certificateDTO==null){
            return null;
        }
        Certificate certificate = new Certificate(
                certificateDTO.getName(),
                certificateDTO.getDescription(),
                certificateDTO.getPrice(),
                certificateDTO.getDuration()
        );
        return certificate;
    }

    @Override
    public CertificateDTO toDto(Certificate certificate, List<TagDTO> tags) {
        if (certificate==null){
            return null;
        }
        CertificateDTO certificateDTO = new CertificateDTO(
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

