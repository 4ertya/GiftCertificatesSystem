package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SQLCertificatesDAO implements CertificateDAO {


    private final static String READ_ALL_CERTIFICATES = "SELECT gc.*, string_agg(t.name, ', ') tags\n" +
            "FROM gift_certificates gc\n" +
            "JOIN gift_certificates_tags gct on gct.gift_certificates_id = gc.id\n" +
            "JOIN tags t on t.id = gct.tags_id\n" +
            "group by gc.id;";
    private final static String READ_CERTIFICATE_BY_ID = "SELECT gc.*, string_agg(t.name, ', ') tags\n" +
            "FROM gift_certificates gc\n" +
            "JOIN gift_certificates_tags gct on gct.gift_certificates_id = gc.id\n" +
            "JOIN tags t on t.id = gct.tags_id\n" +
            "WHERE gc.id=? \n" +
            "group by gc.id;";
    private final static String CREATE_CERTIFICATE = "WITH input_data(name, description, price, duration, tags) " +
            "AS (\n" +
            "VALUES (?,?,?,?,?)\n" +
            "),\n" +
            "input_tags AS (\n" +
            "SELECT DISTINCT tag     -- fold duplicates\n" +
            "FROM input_data, unnest(tags::text[]) tag\n" +
            ")\n" +
            ",gc AS (  -- insert into questions\n" +
            "INSERT INTO gift_certificates (name, description, price, duration)\n" +
            "SELECT name, description, price, duration\n" +
            "FROM   input_data\n" +
            "RETURNING gift_certificates.id\n" +
            ")\n" +
            ",t AS (  -- insert into tags\n" +
            "INSERT INTO tags (name)\n" +
            "TABLE  input_tags  -- short for: SELECT * FROM input_tags\n" +
            "ON CONFLICT (name) DO NOTHING    -- only new tags\n" +
            "RETURNING tags.id\n" +
            ")\n" +
            "INSERT INTO gift_certificates_tags (gift_certificates_id, tags_id)\n" +
            "SELECT gc.id, t.id\n" +
            "FROM   gc, (\n" +
            "SELECT t.id FROM t\n" +
            "UNION  ALL\n" +
            "SELECT id FROM input_tags JOIN tags ON tag=tags.name\n" +
            ") t ;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Certificate> readAll() {
        return jdbcTemplate.query(READ_ALL_CERTIFICATES, new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public Certificate read(int id) {
        return jdbcTemplate.query(READ_CERTIFICATE_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Certificate.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration());
        namedParameterJdbcTemplate.update(CREATE_CERTIFICATE, parameterSource, keyHolder);
        if (keyHolder.getKey() == null) {
            //TODO: - Entity not added exception
        }
        return read((Integer) keyHolder.getKey());
    }

    @Override
    public Certificate update(Certificate certificate) {
        return null;
    }
}

