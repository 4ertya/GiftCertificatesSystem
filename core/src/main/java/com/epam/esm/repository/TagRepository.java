package com.epam.esm.repository;



import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    List<Tag> findAll();

    Optional<Tag> findByTagId(long id);

    Optional<Tag> findByTagName(String name);

    List<Tag> findByCertificateId(long id);

    Tag create(Tag tag);

    void update(Tag tag);

    void delete(long id);
}
