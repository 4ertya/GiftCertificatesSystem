package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final TagMapper tagMapper;


    @Override
    public List<TagDTO> readAll() {
        return tagMapper.toDtoList(tagDAO.findAll());
    }

    @Override
    public TagDTO read(int id) {
        return tagMapper.toDto(tagDAO.findById(id));
    }

    @Override
    public List<TagDTO> findByCertificateId(int certificateId) {
        return tagMapper.toDtoList(tagDAO.findByCertificateId(certificateId));
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        Tag tag = tagDAO.create(tagMapper.toEntity(tagDTO));
        return tagMapper.toDto(tagDAO.findById(tag.getId()));
    }

    @Override
    public TagDTO update(int id, TagDTO tagDTO) {
        return null;
    }
}
