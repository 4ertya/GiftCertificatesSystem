package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;


    @Override
    public List<TagDTO> findAllTags() {
        List<Tag> tags = tagRepository.findAllTags();
        return tagMapper.toDtoList(tags);
    }

    @Override
    public TagDTO findTagById(long id) {
        Tag tag = tagRepository.findTagById(id).orElseThrow(EntityNotFoundException::new);
        return tagMapper.toDto(tag);
    }

    @Override
    public List<TagDTO> findTagByCertificateId(long certificateId) {
        List<Tag> tags = tagRepository.findTagByCertificateId(certificateId);
        return tagMapper.toDtoList(tags);
    }

    @Override
    public TagDTO createTag(TagDTO tagDTO) {
        Optional<Tag> tag = tagRepository.findTagByName(tagDTO.getName());
        if (tag.isPresent()) {
            tagDTO.setId(tag.get().getId());
        } else {
            Tag created = tagRepository.createTag(tagMapper.toEntity(tagDTO));
            tagDTO.setId(created.getId());
        }
        return tagDTO;
    }

    @Override
    public TagDTO updateTag(TagDTO tagDTO) {
        tagRepository.findTagById(tagDTO.getId()).orElseThrow(EntityNotFoundException::new);
        if (tagRepository.findTagByName(tagDTO.getName()).isPresent()) {
            throw new EntityAlreadyExistException();
        }
        Tag tag = tagMapper.toEntity(tagDTO);
        tag.setId(tagDTO.getId());
        tagRepository.updateTag(tag);
        return tagDTO;
    }

    @Override
    @Transactional
    public void deleteTag(long id) {
        tagRepository.findTagById(id).orElseThrow(EntityNotFoundException::new);
        tagRepository.unbindByTagId(id);
        tagRepository.deleteTag(id);
    }

    @Override
    public void bind(long certificateId, long tagId) {
        tagRepository.bind(certificateId, tagId);
    }

    @Override
    public void unbindByCertificateId(long certificateId) {
        tagRepository.unbindByCertificateId(certificateId);
    }

    @Override
    public void unbindByTagId(long tagId) {
        tagRepository.unbindByTagId(tagId);
    }
}
