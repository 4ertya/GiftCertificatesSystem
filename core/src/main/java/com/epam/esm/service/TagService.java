package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {

    List<TagDTO> findAllTags();

    TagDTO findTagById(long id);

    List<TagDTO> findTagByCertificateId(long certificateId);

    TagDTO createTag(TagDTO tagDTO);

    TagDTO updateTag(TagDTO tagDTO);

    void deleteTag(long id);
}
