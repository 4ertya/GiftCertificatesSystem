package com.epam.esm.service.impl;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotAddedException;
import com.epam.esm.exception.EntityNotDeletedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityNotUpdatedException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateDAO;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertificateServiceImpl implements CertificatesService {

    private final CertificateDAO certificateDAO;
    private final TagService tagService;
    private final CertificateTagService certificateTagService;
    private final CertificateMapper certificateMapper;


    @Override
    public List<CertificateDTO> readAll() {
        List<Certificate> certificates = certificateDAO.readAll();
        if (certificates.isEmpty()) {
            return null;
        }
        List<CertificateDTO> certificateDTOS = new LinkedList<>();
        for (Certificate certificate : certificates) {
            List<TagDTO> tags = tagService.findByCertificateId(certificate.getId());
            certificateDTOS.add(certificateMapper.toDto(certificate, tags));
        }
        return certificateDTOS;
    }

    @Override
    public CertificateDTO read(int id) {
        Certificate certificate = certificateDAO.read(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificate"));
        List<TagDTO> tags = tagService.findByCertificateId(id);
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    public CertificateDTO create(CertificateDTO certificateDTO) {

        Certificate certificate = certificateDAO.create(certificateMapper.toEntity(certificateDTO))
                .orElseThrow(() -> new EntityNotAddedException("Certificate"));
        for (TagDTO tagDTO : certificateDTO.getTags()) {
            int tagId = tagService.create(tagDTO).getId();
            certificateTagService.add(certificate.getId(), tagId);
        }
        List<TagDTO> tags = tagService.findByCertificateId(certificate.getId());
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    public CertificateDTO update(int id, CertificateDTO certificateDTO) {
        Certificate newCertificate = certificateMapper.toEntity(certificateDTO);
        newCertificate.setId(id);
        Certificate certificate = certificateDAO.update(newCertificate).orElseThrow(() -> new EntityNotUpdatedException("Certificate", id));
        for (TagDTO tagDTO : certificateDTO.getTags()) {
            TagDTO tag = tagService.create(tagDTO);
            if (tag != null) {
                int tagId = tag.getId();
                certificateTagService.add(certificate.getId(), tagId);
            }
        }
        List<TagDTO> tags = tagService.findByCertificateId(id);
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    public CertificateDTO delete(int id) {
        List<TagDTO> tags = tagService.findByCertificateId(id);
        certificateTagService.deleteByCertificateId(id);
        Certificate certificate = certificateDAO.delete(id).orElseThrow(() -> new EntityNotDeletedException("Certificate", id));
        return certificateMapper.toDto(certificate, tags);
    }

}
