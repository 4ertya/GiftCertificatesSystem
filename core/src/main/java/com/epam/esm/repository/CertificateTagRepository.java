package com.epam.esm.repository;

public interface CertificateTagRepository {

    void add(long certificateId, long tagId);

    void deleteByCertificateId(long certificateId);

    void deleteByTagId(long certificateId);

    Integer delete(long certificateId, long tagId);
}
