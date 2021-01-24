package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateTagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Spy
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;
    @Mock
    CertificateTagService certificateTagService;

    @Nested
    @SuiteDisplayName("ReadAll()")
    class ReadAll {
        @Test
        @DisplayName("Read all tags with result")
        void readAllWithResult() {
            Tag tag = new Tag();
            List<Tag> tags = Stream.of(tag).collect(Collectors.toList());
            List<TagDTO> tagDTOS = new ArrayList<>();

            when(tagRepository.findAll()).thenReturn(tags);
            when(tagMapper.toDtoList(tags)).thenReturn(tagDTOS);

            List<TagDTO> actual = tagService.readAllTags();
            assertEquals(tagDTOS, actual);
        }

        @Test
        @DisplayName("Read all tags without result")
        void readAllWithNull() {

            List<Tag> tags = new ArrayList<>();

            when(tagRepository.findAll()).thenReturn(tags);
            when(tagMapper.toDtoList(new ArrayList<>())).thenReturn(null);

            List<TagDTO> actual = tagService.readAllTags();

            assertNull(actual);
        }
    }

    @Nested
    @SuiteDisplayName("Read()")
    class Read {
        @Test
        void readSuccessful() {
            int tagId = 1;
            Tag tag = new Tag();
            TagDTO tagDTO = new TagDTO();

            when(tagRepository.findByTagId(tagId)).thenReturn(Optional.of(tag));
            when(tagMapper.toDto(tag)).thenReturn(tagDTO);

            TagDTO actual = tagService.read(tagId);

            assertEquals(tagDTO, actual);
        }

        @Test
        void readUnsuccessful() {
            int tagId = 1;

            when(tagRepository.findByTagId(tagId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> tagService.read(tagId));
        }
    }

    @Nested
    @SuiteDisplayName("FindByCertificateId()")
    class FindByCertificateId {
        @Test
        void readSuccessful() {
            int certificateId = 1;
            Tag tag = new Tag();
            TagDTO tagDTO = new TagDTO();
            List<TagDTO> tagDTOS = Stream.of(tagDTO).collect(Collectors.toList());
            List<Tag> tags = Stream.of(tag).collect(Collectors.toList());

            when(tagRepository.findByCertificateId(certificateId)).thenReturn(tags);
            when(tagMapper.toDtoList(tags)).thenReturn(tagDTOS);

            List<TagDTO> actual = tagService.findByCertificateId(certificateId);

            assertEquals(tagDTOS, actual);
        }

        @Test
        void readUnsuccessful() {
            int certificateId = 1;

            when(tagRepository.findByCertificateId(certificateId)).thenReturn(new ArrayList<>());
            when(tagMapper.toDtoList(new ArrayList<>())).thenReturn(null);
            List<TagDTO> actual = tagService.findByCertificateId(certificateId);
            assertNull(actual);
        }
    }

    @Nested
    @SuiteDisplayName("Create()")
    class Create {
        @Test
        @DisplayName("Successful")
        void createSuccessful() {

            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(1L);
            Tag tag = new Tag();
            tag.setId(1L);
            when(tagRepository.findByTagName(tagDTO.getName())).thenReturn(Optional.empty());
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
            when(tagRepository.create(tag)).thenReturn(tag);
            TagDTO actual = tagService.create(tagDTO);
            assertEquals(tagDTO, actual);
        }

    }

    @Nested
    @SuiteDisplayName("Update()")
    class Update {
        @Test
        @DisplayName("Successful")
        void updateSuccessful() {
            long tagId = 1;
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tagId);
            Tag tag = new Tag();
            when(tagRepository.findByTagId(tagId)).thenReturn(Optional.of(tag));
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);

            TagDTO actual = tagService.update(tagDTO);
            assertEquals(tagDTO, actual);
            verify(tagRepository).update(tag);
        }

        @Test
        @DisplayName("Successful")
        void updateUnsuccessful() {
            long tagId = 1;
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tagId);
            when(tagRepository.findByTagId(tagId)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> tagService.update(tagDTO));
        }
    }

    @Nested
    @SuiteDisplayName("Delete()")
    class Delete {

        @Test
        void deleteSuccessful() {
            long tagId = 1;
            when(tagRepository.findByTagId(tagId)).thenReturn(Optional.of(new Tag()));
            tagService.delete(tagId);
            verify(certificateTagService).deleteByTagId(tagId);
            verify(tagRepository).delete(tagId);
        }
    }
}