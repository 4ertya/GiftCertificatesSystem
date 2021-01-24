package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {

    List<TagDTO> readAll();

    TagDTO read(long id);

    List<TagDTO> findByCertificateId(long certificateId);

    TagDTO create(TagDTO tagDTO);

    TagDTO update(long id, TagDTO tagDTO);

    TagDTO delete(long id);
}
