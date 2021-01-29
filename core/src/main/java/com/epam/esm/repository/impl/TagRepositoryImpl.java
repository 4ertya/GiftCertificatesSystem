package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
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
public class TagRepositoryImpl implements TagRepository {

    private final static String SELECT_ALL_TAGS = "SELECT * FROM tags;";
    private final static String SELECT_TAG_BY_ID = "SELECT * FROM tags WHERE id=?;";
    private final static String SELECT_TAG_BY_NAME = "SELECT * FROM tags WHERE name=?;";
    private final static String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT * FROM tags " +
            "JOIN certificates_tags gct ON tags.id = gct.tags_id WHERE gct.gift_certificates_id=?;";
    private final static String INSERT_TAG = "INSERT INTO tags(name) VALUES (:name);";
    private final static String UPDATE_TAG = "UPDATE tags SET name=? WHERE id=?;";
    private final static String DELETE_TAG = "DELETE FROM tags WHERE id=?;";
    private final static String BIND_TAG = "INSERT INTO certificates_tags (gift_certificates_id, tags_id) VALUES (?,?) ON CONFLICT(gift_certificates_id, tags_id) DO NOTHING;";
    private final static String UNBIND_BY_CERTIFICATE_ID = "DELETE FROM certificates_tags WHERE gift_certificates_id=?";
    private final static String UNBIND_BY_TAG_ID = "DELETE FROM certificates_tags WHERE tags_id=? ";


    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Tag> findAllTags() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findTagById(long id) {
        return jdbcTemplate.query(SELECT_TAG_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class))
                .stream()
                .findAny();
    }

    @Override
    public List<Tag> findTagByCertificateId(long id) {
        return jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag createTag(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", tag.getName());
        namedParameterJdbcTemplate.update(INSERT_TAG, parameterSource, keyHolder, new String[]{"id"});
        tag.setId(keyHolder.getKey().longValue());

        return tag;
    }

    @Override
    public void updateTag(Tag tag) {
        jdbcTemplate.update(UPDATE_TAG, tag.getName(), tag.getId());
    }

    @Override
    public void deleteTag(long id) {
        jdbcTemplate.update(DELETE_TAG, id);
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return jdbcTemplate.query(SELECT_TAG_BY_NAME, new Object[]{name}, new BeanPropertyRowMapper<>(Tag.class))
                .stream()
                .findAny();
    }

    @Override
    public void bind(long certificateId, long tagId) {
        jdbcTemplate.update(BIND_TAG, certificateId, tagId);
    }

    @Override
    public void unbindByCertificateId(long certificateId) {
        jdbcTemplate.update(UNBIND_BY_CERTIFICATE_ID, certificateId);
    }

    @Override
    public void unbindByTagId(long tagId) {
        jdbcTemplate.update(UNBIND_BY_TAG_ID, tagId);
    }
}
