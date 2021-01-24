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
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.SpecificationCreator;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificatesService {

    private final CertificateDAO certificateDAO;
    private final TagService tagService;
    private final CertificateTagService certificateTagService;
    private final CertificateMapper certificateMapper;
    private final SpecificationCreator specificationCreator;


    @Override
    @Transactional
    public List<CertificateDTO> readAll(String tagName, String partName, String partDescription, String dateSort, String nameSort) {
        Optional<Specification> receiveSpecification = specificationCreator.receiveSpecification(tagName, partName, partDescription, dateSort, nameSort);

        List<Certificate> certificates = receiveSpecification.isPresent() ? certificateDAO.readAllBySpecification(receiveSpecification.get()) : certificateDAO.readAll();
        if (certificates.isEmpty()) {
            return null;
        }
        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates) {
            List<TagDTO> tags = tagService.findByCertificateId(certificate.getId());
            certificateDTOS.add(certificateMapper.toDto(certificate, tags));
        }
        return certificateDTOS;
    }

    @Override
    @Transactional
    public CertificateDTO read(long id) {
        Certificate certificate = certificateDAO.read(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificate"));
        List<TagDTO> tags = tagService.findByCertificateId(id);
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {

        Certificate certificate = certificateDAO.create(certificateMapper.toEntity(certificateDTO))
                .orElseThrow(() -> new EntityNotAddedException("Certificate"));
        for (TagDTO tagDTO : certificateDTO.getTags()) {
            long tagId = tagService.create(tagDTO).getId();
            certificateTagService.add(certificate.getId(), tagId);
        }
        List<TagDTO> tags = tagService.findByCertificateId(certificate.getId());
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    @Transactional
    public CertificateDTO update(long id, CertificateDTO certificateDTO) {
        Certificate newCertificate = certificateMapper.toEntity(certificateDTO);
        newCertificate.setId(id);
        Certificate certificate = certificateDAO.update(newCertificate).orElseThrow(() -> new EntityNotUpdatedException("Certificate", id));
        if (certificateDTO.getTags() != null) {
            for (TagDTO tagDTO : certificateDTO.getTags()) {
                TagDTO tag = tagService.create(tagDTO);
                if (tag != null) {
                    long tagId = tag.getId();
                    certificateTagService.add(certificate.getId(), tagId);
                }
            }
        }
        List<TagDTO> tags = tagService.findByCertificateId(id);
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    @Transactional
    public CertificateDTO delete(long id) {
        List<TagDTO> tags = tagService.findByCertificateId(id);
        certificateTagService.deleteByCertificateId(id);
        Certificate certificate = certificateDAO.delete(id).orElseThrow(() -> new EntityNotDeletedException("Certificate", id));
        return certificateMapper.toDto(certificate, tags);
    }

}
