package com.epam.esm;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;

public class CertificateMapper implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();

        certificate.setId(rs.getInt("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getInt("price"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setCreateDate(ZonedDateTime.from((TemporalAccessor) rs.getTimestamp("create_date")));
        certificate.setCreateDate(ZonedDateTime.from((TemporalAccessor) rs.getTimestamp("last_update_date")));

        return certificate;
    }
}
