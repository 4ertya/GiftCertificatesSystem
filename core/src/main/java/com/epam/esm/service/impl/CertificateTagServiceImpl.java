package com.epam.esm.service.impl;

import com.epam.esm.repository.CertificateTagRepository;
import com.epam.esm.service.CertificateTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertificateTagServiceImpl implements CertificateTagService {

    private final CertificateTagRepository certificateTagRepository;

    @Override
    public void add(long certificateId, long tagId) {
        certificateTagRepository.add(certificateId, tagId);
    }

    @Override
    public void deleteByCertificateId(long certificateId) {
        certificateTagRepository.deleteByCertificateId(certificateId);
    }

    @Override
    public void deleteByTagId(long tagId) {
        certificateTagRepository.deleteByTagId(tagId);
    }

}
