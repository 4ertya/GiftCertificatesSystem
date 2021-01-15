package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;

import java.util.List;

public interface TagService {

    List<TagDTO> readAll();

    TagDTO read(int id);

    List<TagDTO> findByCertificateId(int certificateId);

    TagDTO create(TagDTO tagDTO);

    TagDTO update(int id, TagDTO tagDTO);

    TagDTO delete(int id);
}
