package com.epam.esm.repository.impl;

import com.epam.esm.repository.CertificateTagsDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SQLCertificateTagsDAO implements CertificateTagsDAO {

    private final static String INSERT_QUERY = "INSERT INTO certificates_tags (gift_certificates_id, tags_id) VALUES (?,?) ON CONFLICT(gift_certificates_id, tags_id) DO NOTHING;";
    private final static String DELETE_QUERY = "DELETE FROM certificates_tags WHERE gift_certificates_id=? AND tags_id=? ";
    private final static String DELETE_BY_CERTIFICATE_ID_QUERY = "DELETE FROM certificates_tags WHERE gift_certificates_id=?";
    private final static String DELETE_BY_TAG_ID_QUERY = "DELETE FROM certificates_tags WHERE tags_id=? ";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer add(int certificateId, int tagId) {
        return jdbcTemplate.update(INSERT_QUERY, certificateId, tagId);
    }

    @Override
    public Integer deleteByCertificateId(int certificateId) {
        return jdbcTemplate.update(DELETE_BY_CERTIFICATE_ID_QUERY, certificateId);
    }

    @Override
    public Integer deleteByTagId(int tagId) {
        return jdbcTemplate.update(DELETE_BY_TAG_ID_QUERY, tagId);
    }

    @Override
    public Integer delete(int certificateId, int tagId) {
        return jdbcTemplate.update(DELETE_QUERY, certificateId, tagId);
    }
}
