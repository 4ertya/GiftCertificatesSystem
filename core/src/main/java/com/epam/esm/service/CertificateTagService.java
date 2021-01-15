package com.epam.esm.service;

public interface CertificateTagService {

    Integer add(int certificateId, int tagId);

    Integer deleteByCertificateId(int certificateId);
    Integer deleteByTagId(int tagId);
}
