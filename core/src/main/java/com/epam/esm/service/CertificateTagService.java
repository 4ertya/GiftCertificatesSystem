package com.epam.esm.service;

public interface CertificateTagService {

    Integer add(long certificateId, long tagId);

    Integer deleteByCertificateId(long certificateId);
    Integer deleteByTagId(long tagId);
}
