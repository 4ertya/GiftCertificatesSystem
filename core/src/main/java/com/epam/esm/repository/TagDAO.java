package com.epam.esm.repository;



import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO {

    List<Tag> findAll();

    Optional<Tag> findById(int id);

    List<Tag> findByCertificateId(int id);

    Optional<Tag> create(Tag tag);

    Optional<Tag> update(Tag tag);

    Optional<Tag> delete(int id);
}
