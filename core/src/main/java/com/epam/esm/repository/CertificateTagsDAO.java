package com.epam.esm.repository;

public interface CertificateTagsDAO {

    Integer add (int certificateId, int tagId);
    Integer deleteByCertificateId (int certificateId);
    Integer deleteByTagId (int certificateId);
    Integer delete (int certificateId, int tagId);
}
