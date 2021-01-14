package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagMapperImpl implements TagMapper {
    @Override
    public Tag toEntity(TagDTO tagDTO) {
        return new Tag(tagDTO.getName());
    }

    @Override
    public TagDTO toDto(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName(tag.getName());
        tagDTO.setId(tag.getId());
        return tagDTO;
    }

    @Override
    public List<TagDTO> toDtoList(List<Tag> tags) {
        List<TagDTO> tagDTOs;
        tagDTOs = tags.stream().map(tag -> {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tag.getId());
            tagDTO.setName(tag.getName());
            return tagDTO;
        }).collect(Collectors.toList());
        return tagDTOs;
    }
}
