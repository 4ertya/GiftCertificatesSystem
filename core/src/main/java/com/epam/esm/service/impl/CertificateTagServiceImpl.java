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
    public Integer add(long certificateId, long tagId) {
        return certificateTagRepository.add(certificateId, tagId);
    }

    @Override
    public Integer deleteByCertificateId(long certificateId) {
        return certificateTagRepository.deleteByCertificateId(certificateId);
    }

    @Override
    public Integer deleteByTagId(long tagId) {
        return certificateTagRepository.deleteByTagId(tagId);
    }

}
