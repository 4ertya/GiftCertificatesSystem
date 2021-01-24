package com.epam.esm.repository;

public interface CertificateTagsDAO {

    Integer add (long certificateId, long tagId);
    Integer deleteByCertificateId (long certificateId);
    Integer deleteByTagId (long certificateId);
    Integer delete (long certificateId, long tagId);
}
