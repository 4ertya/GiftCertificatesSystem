package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final TagMapper tagMapper;
    private final CertificateTagService certificateTagService;


    @Override
    public List<TagDTO> readAllTags() {
        List<Tag> tags = tagDAO.findAll();
        return tagMapper.toDtoList(tags);
    }

    @Override
    public TagDTO read(long id) {
        Tag tag = tagDAO.findByTagId(id).orElseThrow(() -> new EntityNotFoundException("Tag"));
        return tagMapper.toDto(tag);
    }

    @Override
    public List<TagDTO> findByCertificateId(long certificateId) {
        List<Tag> tags = tagDAO.findByCertificateId(certificateId);
        return tagMapper.toDtoList(tags);
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        Optional<Tag> tag = tagDAO.findByTagName(tagDTO.getName());
        if (tag.isPresent()) {
            tagDTO.setId(tag.get().getId());
        } else {
            Tag created = tagDAO.create(tagMapper.toEntity(tagDTO));
            tagDTO.setId(created.getId());
        }
        return tagDTO;
    }

    @Override
    public TagDTO update(TagDTO tagDTO) {
        tagDAO.findByTagId(tagDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Tag"));
        Tag tag = tagMapper.toEntity(tagDTO);
        tag.setId(tagDTO.getId());
        tagDAO.update(tag);
        return tagDTO;
    }

    @Override
    @Transactional
    public void delete(long id) {
        tagDAO.findByTagId(id).orElseThrow(() -> new EntityNotFoundException("Tag"));
        certificateTagService.deleteByTagId(id);
        tagDAO.delete(id);
    }
}
