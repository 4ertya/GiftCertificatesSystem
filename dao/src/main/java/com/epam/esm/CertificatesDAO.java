package com.epam.esm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificatesDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificatesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Certificate> index(){
        return jdbcTemplate.query("SELECT * FROM gift_certificates", new CertificateMapper());
    }


}

