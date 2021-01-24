package com.epam.esm.repository;



import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO {

    List<Tag> findAll();

    Optional<Tag> findById(long id);

    List<Tag> findByCertificateId(long id);

    Optional<Tag> create(Tag tag);

    Optional<Tag> update(Tag tag);

    Optional<Tag> delete(long id);
}
