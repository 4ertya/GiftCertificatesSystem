package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotAddedException;
import com.epam.esm.exception.EntityNotDeletedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityNotUpdatedException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final TagMapper tagMapper;
    private final CertificateTagService certificateTagService;


    @Override
    public List<TagDTO> readAll() {
        List<Tag> tags = tagDAO.findAll();
        return tags.isEmpty() ? null : tagMapper.toDtoList(tagDAO.findAll());
    }

    @Override
    public TagDTO read(long id) {
        Tag tag = tagDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag"));
        return tagMapper.toDto(tag);
    }

    @Override
    public List<TagDTO> findByCertificateId(long certificateId) {
        List<Tag> tags = tagDAO.findByCertificateId(certificateId);
        return tags.isEmpty() ? null : tagMapper.toDtoList(tags);
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        Tag tag = tagDAO.create(tagMapper.toEntity(tagDTO)).orElseThrow(() -> new EntityNotAddedException("Tag"));
        return read(tag.getId());
    }

    @Override
    public TagDTO update(long id, TagDTO tagDTO) {
        Tag tag = tagMapper.toEntity(tagDTO);
        tag.setId(id);
        Tag updatedTag = tagDAO.update(tag).orElseThrow(() -> new EntityNotUpdatedException("Tag", id));
        return tagMapper.toDto(updatedTag);
    }

    @Override
    public TagDTO delete(long id) {
        certificateTagService.deleteByTagId(id);
        Tag tag = tagDAO.delete(id).orElseThrow(()-> new EntityNotDeletedException("Tag", id));
        return tagMapper.toDto(tag);
    }
}
