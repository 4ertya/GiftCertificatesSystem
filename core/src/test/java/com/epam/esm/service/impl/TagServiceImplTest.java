package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
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


    @Nested
    @SuiteDisplayName("ReadAll()")
    class ReadAll {
        @Test
        @DisplayName("Read all tags with result")
        void readAllWithResult() {
            Tag tag = new Tag();
            List<Tag> tags = Stream.of(tag).collect(Collectors.toList());
            List<TagDTO> tagDTOS = new ArrayList<>();

            when(tagRepository.findAllTags()).thenReturn(tags);
            when(tagMapper.toDtoList(tags)).thenReturn(tagDTOS);

            List<TagDTO> actual = tagService.findAllTags();
            assertEquals(tagDTOS, actual);
        }

        @Test
        @DisplayName("Read all tags without result")
        void readAllWithNull() {

            List<Tag> tags = new ArrayList<>();

            when(tagRepository.findAllTags()).thenReturn(tags);
            when(tagMapper.toDtoList(new ArrayList<>())).thenReturn(null);

            List<TagDTO> actual = tagService.findAllTags();

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

            when(tagRepository.findTagById(tagId)).thenReturn(Optional.of(tag));
            when(tagMapper.toDto(tag)).thenReturn(tagDTO);

            TagDTO actual = tagService.findTagById(tagId);

            assertEquals(tagDTO, actual);
        }

        @Test
        void readUnsuccessful() {
            int tagId = 1;

            when(tagRepository.findTagById(tagId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> tagService.findTagById(tagId));
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

            when(tagRepository.findTagByCertificateId(certificateId)).thenReturn(tags);
            when(tagMapper.toDtoList(tags)).thenReturn(tagDTOS);

            List<TagDTO> actual = tagService.findTagByCertificateId(certificateId);

            assertEquals(tagDTOS, actual);
        }

        @Test
        void readUnsuccessful() {
            int certificateId = 1;

            when(tagRepository.findTagByCertificateId(certificateId)).thenReturn(new ArrayList<>());
            when(tagMapper.toDtoList(new ArrayList<>())).thenReturn(null);
            List<TagDTO> actual = tagService.findTagByCertificateId(certificateId);
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
            when(tagRepository.findTagByName(tagDTO.getName())).thenReturn(Optional.empty());
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
            when(tagRepository.createTag(tag)).thenReturn(tag);
            TagDTO actual = tagService.createTag(tagDTO);
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
            tagDTO.setName("aaa");
            tagDTO.setId(tagId);
            Tag tag = new Tag();
            tag.setName("aa");
            when(tagRepository.findTagById(tagId)).thenReturn(Optional.of(tag));
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
            TagDTO actual = tagService.updateTag(tagDTO);
            assertEquals(tagDTO, actual);
            verify(tagRepository).updateTag(tag);
        }

        @Test
        @DisplayName("Successful")
        void updateUnsuccessful() {
            long tagId = 1;
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tagId);
            when(tagRepository.findTagById(tagId)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> tagService.updateTag(tagDTO));
        }
    }

    @Nested
    @SuiteDisplayName("Delete()")
    class Delete {

        @Test
        void deleteSuccessful() {
            long tagId = 1;
            when(tagRepository.findTagById(tagId)).thenReturn(Optional.of(new Tag()));
            tagService.deleteTag(tagId);
            verify(tagRepository).unbindByTagId(tagId);
            verify(tagRepository).deleteTag(tagId);
        }
    }
}