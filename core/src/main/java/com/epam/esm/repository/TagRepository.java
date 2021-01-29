package com.epam.esm.repository;


import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    List<Tag> findAllTags();

    Optional<Tag> findTagById(long id);

    Optional<Tag> findTagByName(String name);

    List<Tag> findTagByCertificateId(long id);

    Tag createTag(Tag tag);

    void updateTag(Tag tag);

    void deleteTag(long id);

    void bind(long certificateId, long tagId);

    void unbindByCertificateId(long certificateId);

    void unbindByTagId(long certificateId);
}
