package com.epam.esm.mapper;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;

import java.util.List;

public interface CertificateConverter {
    Certificate toEntity(CertificateDTO certificateDTO);

    CertificateDTO toDto(Certificate certificate, List<Tag> tags);
}
