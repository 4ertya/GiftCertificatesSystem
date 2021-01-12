package com.epam.esm.converter;

import com.epam.esm.entity.Certificate;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface CertificateConverter {
    Certificate toEntity(CertificateDTO certificateDTO);

    CertificateDTO toDto(Certificate certificate, List<Tag> tags);
}
