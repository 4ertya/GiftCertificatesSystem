package com.epam.esm.repository;

public interface CertificateTagRepository {

    Integer add (long certificateId, long tagId);
    Integer deleteByCertificateId (long certificateId);
    Integer deleteByTagId (long certificateId);
    Integer delete (long certificateId, long tagId);
}
