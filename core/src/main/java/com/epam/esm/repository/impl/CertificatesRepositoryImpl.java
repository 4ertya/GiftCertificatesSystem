package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.specification.Specification;
import lombok.RequiredArgsConstructor;
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
public class CertificatesRepositoryImpl implements CertificateRepository {


    private final static String SELECT_ALL_CERTIFICATES_BY_SPECIFICATION = "SELECT certificates.* FROM certificates JOIN certificates_tags ct on certificates.id = ct.gift_certificates_id JOIN tags  on tags.id = ct.tags_id WHERE 1=1";
    private final static String SELECT_ALL_CERTIFICATES = "SELECT * FROM certificates;";
    private final static String SELECT_CERTIFICATE_BY_ID = "SELECT * FROM certificates WHERE id=?;";
    private final static String CREATE_CERTIFICATE = "INSERT INTO certificates (name, description, price, duration) VALUES (:name,:description,:price,:duration);";
    private final static String UPDATE_CERTIFICATE = "UPDATE certificates set " +
            "name=COALESCE(?,name), " +
            "description=COALESCE(?,description), " +
            "price=COALESCE(?,price), " +
            "duration =COALESCE(?,duration), " +
            "last_update_date=DEFAULT WHERE id=?;";
    private final static String DELETE_CERTIFICATE = "DELETE FROM certificates WHERE id=?;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Certificate> findAllCertificates() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES, new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public List<Certificate> findAllCertificatesBySpecification(Specification specification) {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES_BY_SPECIFICATION + specification.toSqlRequest(), specification.receiveParameters(), new BeanPropertyRowMapper<>(Certificate.class));
    }

    @Override
    public Optional<Certificate> findCertificateById(long id) {
        return jdbcTemplate.query(SELECT_CERTIFICATE_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Certificate.class))
                .stream()
                .findAny();
    }

    @Override
    public Certificate createCertificate(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration());
        namedParameterJdbcTemplate.update(CREATE_CERTIFICATE, parameterSource, keyHolder, new String[]{"id"});
        certificate.setId(keyHolder.getKey().longValue());
        return certificate;
    }

    @Override
    public void updateCertificate(Certificate certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(), certificate.getDuration(), certificate.getId());
    }

    @Override
    public void deleteCertificate(long id) {
        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }
}

