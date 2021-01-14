package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;

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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SQLTagDAO implements TagDAO {

    private final static String SELECT_ALL_QUERY = "SELECT * FROM tags;";
    private final static String SELECT_BY_ID_QUERY = "SELECT * FROM tags WHERE id=?;";
    private final static String SELECT_BY_NAME_QUERY = "SELECT * FROM tags WHERE name=?;";
    private final static String SELECT_BY_CERTIFICATE_ID_QUERY = "SELECT * FROM tags " +
            "JOIN certificates_tags gct ON tags.id = gct.tags_id WHERE gct.gift_certificates_id=?;";
    private final static String INSERT_QUERY = "INSERT INTO tags(name) VALUES (:name) ON CONFLICT (name) DO NOTHING;";
    private final static String UPDATE_QUERY = "UPDATE tags SET name=? WHERE id=?;";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag findById(int id) {
        Tag tag = jdbcTemplate.query(SELECT_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class))
                .stream()
                .findAny()
                .orElseThrow(null);
        return tag;
    }

    @Override
    public List<Tag> findByCertificateId(int id) {
        return jdbcTemplate.query(SELECT_BY_CERTIFICATE_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Optional<Tag> temp = Optional.ofNullable(findByName(tag.getName()));

        if (temp.isPresent()) {
            System.out.println(temp.get());
            return temp.get();
        }

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", tag.getName());
        namedParameterJdbcTemplate.update(INSERT_QUERY, parameterSource, keyHolder, new String[]{"id"});
        if (keyHolder.getKey() == null) {
            //TODO: - Entity not added exception
        }
        return findById((Integer) keyHolder.getKey());
    }

    @Override
    public Tag update(Tag tag) {
        if (jdbcTemplate.update(UPDATE_QUERY, tag.getName(), tag.getId()) == 0) {
            //TODO: - Entity not updated exception
        }
        return findById(tag.getId());
    }

    public Tag findByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME_QUERY, new Object[]{name}, new BeanPropertyRowMapper<>(Tag.class))
                .stream()
                .findAny()
                .orElse(null);
    }
}
