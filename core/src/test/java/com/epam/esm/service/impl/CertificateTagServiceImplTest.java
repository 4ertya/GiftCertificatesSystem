package com.epam.esm.service.impl;

import com.epam.esm.repository.CertificateTagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        int rows = 1;
        when(certificateTagRepository.add(certificateId, tagId)).thenReturn(rows);
        int actual = certificateTagService.add(certificateId, tagId);
        assertEquals(rows, actual);
    }

    @Test
    void deleteByCertificateId() {
        int certificateId = 1;
        int rows = 1;
        when(certificateTagRepository.deleteByCertificateId(certificateId)).thenReturn(rows);
        int actual = certificateTagService.deleteByCertificateId(certificateId);
        assertEquals(rows, actual);
    }

    @Test
    void deleteByTagId() {
        int tagId = 1;
        int rows = 1;
        when(certificateTagRepository.deleteByTagId(tagId)).thenReturn(rows);
        int actual = certificateTagService.deleteByTagId(tagId);
        assertEquals(rows, actual);
    }
}