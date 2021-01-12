package com.epam.esm;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDAO {

    List<Tag> findAll();

    Tag findById(int id);

    List<Tag> findByCertificateId(int id);

    Tag create(Tag tag);

    Tag update(Tag tag);
}
