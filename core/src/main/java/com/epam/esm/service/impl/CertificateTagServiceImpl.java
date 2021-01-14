package com.epam.esm.service.impl;

import com.epam.esm.repository.CertificateTagsDAO;
import com.epam.esm.service.CertificateTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertificateTagServiceImpl implements CertificateTagService {

    private final CertificateTagsDAO certificateTagsDAO;

    @Override
    public Integer add(int certificateId, int tagId) {
        return certificateTagsDAO.add(certificateId, tagId);
    }

    @Override
    public Integer remove(int certificateId, int tagId) {
        return certificateTagsDAO.remove(certificateId, tagId);
    }
}