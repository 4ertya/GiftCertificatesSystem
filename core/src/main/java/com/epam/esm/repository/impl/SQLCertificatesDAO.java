package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateDAO;
import com.epam.esm.repository.specification.Specification;
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
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SQLCertificatesDAO implements CertificateDAO {


    private final static String READ_ALL_CERTIFICATES_BY_SPECIFICATION = "SELECT certificates.* FROM certificates JOIN certificates_tags ct on certificates.id = ct.gift_certificates_id JOIN tags  on tags.id = ct.tags_id WHERE 1=1";
    private final static String READ_ALL_CERTIFICATES = "SELECT * FROM certificates;";
    private final static String READ_CERTIFICATE_BY_ID = "SELECT * FROM certificates WHERE id=?;";
    private final static String CREATE_CERTIFICATE = "INSERT INTO certificates (name, description, price, duration) VALUES (:name,:description,:price,:duration);";
    private final static String UPDATE_BY_ID_QUERY = "UPDATE certificates set " +
            "name=COALESCE(?,name), " +
            "description=COALESCE(?,description), " +
            "price=COALESCE(?,price), " +
            "duration =COALESCE(?,duration), " +
            "last_update_date=DEFAULT WHERE id=?;";
    private final static String DELETE_BY_ID_QUERY = "DELETE FROM certificates WHERE id=?;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Certificate> readAll() {
        return jdbcTemplate.query(READ_ALL_CERTIFICATES, new BeanPropertyRowMapper<>(Certificate.class));

    }

    @Override
    public List<Certificate> readAllBySpecification(Specification specification) {
        return jdbcTemplate.query(READ_ALL_CERTIFICATES_BY_SPECIFICATION + specification.toSqlRequest(), specification.receiveParameters(), new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public Optional<Certificate> read(long id) {
        return jdbcTemplate.query(READ_CERTIFICATE_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Certificate.class))
                .stream()
                .findAny();
    }

    @Override
    public Optional<Certificate> create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration());
        namedParameterJdbcTemplate.update(CREATE_CERTIFICATE, parameterSource, keyHolder, new String[]{"id"});
        return keyHolder.getKey() != null ? read((Integer) keyHolder.getKey()) : Optional.empty();
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        int rows = jdbcTemplate.update(UPDATE_BY_ID_QUERY, certificate.getName(), certificate.getDescription(), certificate.getPrice(), certificate.getDuration(), certificate.getId());
        return rows == 0 ? Optional.empty() : read(certificate.getId());
    }

    @Override
    public Optional<Certificate> delete(long id) {
        Optional<Certificate> certificate = read(id);
        if (certificate.isPresent()) {
            jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
        }
        return certificate;
    }
}

