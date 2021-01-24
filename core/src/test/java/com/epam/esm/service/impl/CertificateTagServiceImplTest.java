package com.epam.esm.service.impl;

import com.epam.esm.repository.CertificateTagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CertificateTagServiceImplTest {

    @InjectMocks
    private CertificateTagServiceImpl certificateTagService;

    @Mock
    private CertificateTagRepository certificateTagRepository;


    @Test
    void add() {
        int certificateId = 1;
        int tagId = 1;

        certificateTagService.add(certificateId, tagId);
        verify(certificateTagRepository).add(certificateId,tagId);
    }

    @Test
    void deleteByCertificateId() {
        int certificateId = 1;

        certificateTagService.deleteByCertificateId(certificateId);
        verify(certificateTagRepository).deleteByCertificateId(certificateId);
    }

    @Test
    void deleteByTagId() {
        int tagId = 1;

        certificateTagService.deleteByTagId(tagId);
        verify(certificateTagRepository).deleteByTagId(tagId);
    }
}