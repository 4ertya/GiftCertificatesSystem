package com.epam.esm.service.impl;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.DataSortType;
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
    public List<CertificateDTO> findAllCertificates(String tagName, String partName, String partDescription, DataSortType dateSort, DataSortType nameSort) {
        Optional<Specification> receiveSpecification = specificationCreator.receiveSpecification(tagName, partName, partDescription, dateSort, nameSort);

        List<Certificate> certificates = receiveSpecification.isPresent() ? certificateRepository.findAllCertificatesBySpecification(receiveSpecification.get()) : certificateRepository.findAllCertificates();
        if (certificates.isEmpty()) {
            return null;
        }
        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (Certificate certificate : certificates) {
            List<TagDTO> tags = tagService.findTagByCertificateId(certificate.getId());
            certificateDTOS.add(certificateMapper.toDto(certificate, tags));
        }
        return certificateDTOS;
    }

    @Override
    @Transactional
    public CertificateDTO findCertificateById(long id) {
        Certificate certificate = certificateRepository.findCertificateById(id)
                .orElseThrow(EntityNotFoundException::new);
        List<TagDTO> tags = tagService.findTagByCertificateId(id);
        return certificateMapper.toDto(certificate, tags);
    }

    @Override
    @Transactional
    public CertificateDTO createCertificate(CertificateDTO certificateDTO) {
        Certificate certificate = certificateRepository.createCertificate(certificateMapper.toEntity(certificateDTO));
        for (TagDTO tagDTO : certificateDTO.getTags()) {
            long tagId = tagService.createTag(tagDTO).getId();
            certificateTagService.add(certificate.getId(), tagId);
        }
        List<TagDTO> tags = tagService.findTagByCertificateId(certificate.getId());
        Certificate created = certificateRepository.findCertificateById(certificate.getId()).get();
        return certificateMapper.toDto(created, tags);
    }

    @Override
    @Transactional
    public CertificateDTO updateCertificate(CertificateDTO certificateDTO) {
        certificateRepository.findCertificateById(certificateDTO.getId()).orElseThrow(EntityNotFoundException::new);
        Certificate newCertificate = certificateMapper.toEntity(certificateDTO);
        newCertificate.setId(certificateDTO.getId());
        certificateRepository.updateCertificate(newCertificate);
        updateTags(certificateDTO);
        List<TagDTO> tags = tagService.findTagByCertificateId(certificateDTO.getId());
        Certificate updated = certificateRepository.findCertificateById(certificateDTO.getId()).get();
        return certificateMapper.toDto(updated, tags);
    }

    @Override
    @Transactional
    public void deleteCertificate(long id) {
        certificateRepository.findCertificateById(id).orElseThrow(EntityNotFoundException::new);
        certificateTagService.deleteByCertificateId(id);
        certificateRepository.deleteCertificate(id);
    }

    private void updateTags(CertificateDTO certificateDTO) {
        if (certificateDTO.getTags() != null) {
            certificateTagService.deleteByCertificateId(certificateDTO.getId());
            for (TagDTO tagDTO : certificateDTO.getTags()) {
                TagDTO tag = tagService.createTag(tagDTO);
                if (tag != null) {
                    long tagId = tag.getId();
                    certificateTagService.add(certificateDTO.getId(), tagId);
                }
            }
        }
    }

}
