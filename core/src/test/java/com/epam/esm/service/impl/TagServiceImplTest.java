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
    private TagDAO tagDAO;
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

            when(tagDAO.findAll()).thenReturn(tags);
            when(tagMapper.toDtoList(tags)).thenReturn(tagDTOS);

            List<TagDTO> actual = tagService.readAll();
            assertEquals(tagDTOS, actual);
        }

        @Test
        @DisplayName("Read all tags without result")
        void readAllWithNull() {

            List<Tag> tags = new ArrayList<>();

            when(tagDAO.findAll()).thenReturn(tags);

            List<TagDTO> actual = tagService.readAll();

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

            when(tagDAO.findById(tagId)).thenReturn(Optional.of(tag));
            when(tagMapper.toDto(tag)).thenReturn(tagDTO);

            TagDTO actual = tagService.read(tagId);

            assertEquals(tagDTO, actual);
        }

        @Test
        void readUnsuccessful() {
            int tagId = 1;

            when(tagDAO.findById(tagId)).thenReturn(Optional.empty());

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

            when(tagDAO.findByCertificateId(certificateId)).thenReturn(tags);
            when(tagMapper.toDtoList(tags)).thenReturn(tagDTOS);

            List<TagDTO> actual = tagService.findByCertificateId(certificateId);

            assertEquals(tagDTOS, actual);
        }

        @Test
        void readUnsuccessful() {
            int certificateId = 1;

            when(tagDAO.findByCertificateId(certificateId)).thenReturn(new ArrayList<>());

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

            TagServiceImpl temp = spy(tagService);
            TagDTO tagDTO = new TagDTO();
            Tag tag = new Tag();
            tag.setId(1L);
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
            when(tagDAO.create(tag)).thenReturn(Optional.of(tag));
            doReturn(tagDTO).when(temp).read(tag.getId());
            TagDTO actual = temp.create(tagDTO);
            assertEquals(tagDTO, actual);
        }

        @Test
        @DisplayName("Unsuccessful")
        void createUnsuccessful() {
            TagDTO tagDTO = new TagDTO();
            Tag tag = new Tag();
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
            when(tagDAO.create(tag)).thenReturn(Optional.empty());
            assertThrows(EntityNotAddedException.class, () -> tagService.create(new TagDTO()));
        }
    }

    @Nested
    @SuiteDisplayName("Update()")
    class Update {
        @Test
        @DisplayName("Successful")
        void updateSuccessful() {
            int tagId = 1;
            TagDTO tagDTO = new TagDTO();
            Tag tag = new Tag();
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
            when(tagDAO.update(tag)).thenReturn(Optional.of(tag));
            when(tagMapper.toDto(tag)).thenReturn(tagDTO);
            TagDTO actual = tagService.update(tagId, tagDTO);
            assertEquals(tagDTO, actual);
        }

        @Test
        @DisplayName("Successful")
        void updateUnsuccessful() {
            int tagId = 1;
            TagDTO tagDTO = new TagDTO();
            Tag tag = new Tag();
            when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
            when(tagDAO.update(tag)).thenReturn(Optional.empty());
            assertThrows(EntityNotUpdatedException.class, () -> tagService.update(tagId, tagDTO));
        }
    }

    @Nested
    @SuiteDisplayName("Delete()")
    class Delete {

        @Test
        void deleteSuccessful() {
            int tagId=1;
            Tag tag = new Tag();
            TagDTO tagDTO = new TagDTO();
            when(tagDAO.delete(tagId)).thenReturn(Optional.of(tag));
            when(tagMapper.toDto(tag)).thenReturn(tagDTO);
            TagDTO actual = tagService.delete(tagId);
            assertEquals(tagDTO, actual);
            verify(certificateTagService).deleteByTagId(tagId);
        }

        @Test
        void deleteUnsuccessful() {
            int tagId=1;
            when(tagDAO.delete(tagId)).thenReturn(Optional.empty());
            assertThrows(EntityNotDeletedException.class,()->tagService.delete(tagId));
            verify(certificateTagService).deleteByTagId(tagId);
        }
    }
}