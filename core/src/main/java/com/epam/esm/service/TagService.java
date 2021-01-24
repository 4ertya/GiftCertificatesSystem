package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {

    List<TagDTO> readAllTags();

    TagDTO read(long id);

    List<TagDTO> findByCertificateId(long certificateId);

    TagDTO create(TagDTO tagDTO);

    TagDTO update(TagDTO tagDTO);

    void delete(long id);
}
