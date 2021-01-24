package com.epam.esm.service.impl;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.SpecificationCreator;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagService tagService;
    private final CertificateTagService certificateTagService;
    private final CertificateMapper certificateMapper;
    private final SpecificationCreator specificationCreator;


    @Override
    @Transactional
    public List<CertificateDTO> readAll(String tagName, String partName, String partDescription, String dateSort, String nameSort) {
        Optional<Specification> receiveSpecification = specificationCreator.receiveSpecification(tagName, partName, partDescription, dateSort, nameSort);

        List<Certificate> certificates = receiveSpecification.isPresent() ? certificateRepository.readAllBySpecification(receiveSpecification.get()) : certificateRepository.readAll();
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
        Certificate certificate = certificateRepository.read(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificate"));
        List<TagDTO> tags = tagService.findByCertificateId(id);
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    @Transactional
    public CertificateDTO create(CertificateDTO certificateDTO) {
        Certificate certificate = certificateRepository.create(certificateMapper.toEntity(certificateDTO));
        for (TagDTO tagDTO : certificateDTO.getTags()) {
            long tagId = tagService.create(tagDTO).getId();
            certificateTagService.add(certificate.getId(), tagId);
        }
        List<TagDTO> tags = tagService.findByCertificateId(certificate.getId());
        Certificate created = certificateRepository.read(certificate.getId()).get();
        return certificateMapper.toDto(created, tags);
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        certificateRepository.read(certificateDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Certificate"));
        Certificate newCertificate = certificateMapper.toEntity(certificateDTO);
        newCertificate.setId(certificateDTO.getId());
        certificateRepository.update(newCertificate);
        updateTags(certificateDTO);
        List<TagDTO> tags = tagService.findByCertificateId(certificateDTO.getId());
        Certificate updated = certificateRepository.read(certificateDTO.getId()).get();
        return certificateMapper.toDto(updated, tags);
    }

    @Override
    @Transactional
    public void delete(long id) {
        certificateRepository.read(id).orElseThrow(() -> new EntityNotFoundException("Certificate"));
        certificateTagService.deleteByCertificateId(id);
        certificateRepository.delete(id);
    }

    private void updateTags(CertificateDTO certificateDTO) {
        if (certificateDTO.getTags() != null) {
            certificateTagService.deleteByCertificateId(certificateDTO.getId());
            for (TagDTO tagDTO : certificateDTO.getTags()) {
                TagDTO tag = tagService.create(tagDTO);
                if (tag != null) {
                    long tagId = tag.getId();
                    certificateTagService.add(certificateDTO.getId(), tagId);
                }
            }
        }
    }

}
