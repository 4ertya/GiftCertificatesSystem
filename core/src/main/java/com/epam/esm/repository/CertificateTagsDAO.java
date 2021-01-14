package com.epam.esm.repository;

public interface CertificateTagsDAO {

    Integer add (int certificateId, int tagId);
    Integer remove (int certificateId, int tagId);
}
