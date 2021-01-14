package com.epam.esm.service;

public interface CertificateTagService {

    Integer add(int certificateId, int tagId);

    Integer remove(int certificateId, int tagId);
}
