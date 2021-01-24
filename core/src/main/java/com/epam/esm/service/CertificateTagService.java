package com.epam.esm.service;

public interface CertificateTagService {

    void add(long certificateId, long tagId);

    void deleteByCertificateId(long certificateId);
    void deleteByTagId(long tagId);
}
