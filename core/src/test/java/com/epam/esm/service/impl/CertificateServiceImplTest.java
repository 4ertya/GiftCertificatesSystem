package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotAddedException;
import com.epam.esm.exception.EntityNotDeletedException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.EntityNotUpdatedException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateDAO;
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.SpecificationCreator;
import com.epam.esm.repository.specification.impl.CertificatesBySpecification;
import com.epam.esm.service.CertificateTagService;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificatesService;
    @Mock
    private CertificateDAO certificateDAO;
    @Mock
    private TagService tagService;
    @Mock
    CertificateMapper certificateMapper;
    @Mock
    SpecificationCreator specificationCreator;
    @Mock
    CertificateTagService certificateTagService;

    @Nested
    @SuiteDisplayName("ReadAll()")
    class ReadAll {
        @Test()
        @DisplayName("Read all certificates without specification")
        void readAllWithoutSpecification() {
            long expectedCertificateId = 1;
            List<TagDTO> tagDTOS = new ArrayList<>();
            Certificate certificate = new Certificate();
            certificate.setId(expectedCertificateId);
            List<Certificate> certificates = Stream.of(certificate).collect(Collectors.toList());
            CertificateDTO certificateDTO = new CertificateDTO();
            List<CertificateDTO> certificateDTOS = Stream.of(certificateDTO).collect(Collectors.toList());

            when(certificateDAO.readAll()).thenReturn(certificates);
            when(tagService.findByCertificateId(expectedCertificateId)).thenReturn(tagDTOS);
            when(certificateMapper.toDto(certificate, tagDTOS)).thenReturn(certificateDTO);

            List<CertificateDTO> actual = certificatesService.readAll(null, null, null, null, null);

            assertEquals(certificateDTOS, actual);
        }

        @Test()
        @DisplayName("Read all certificates by specification")
        void readAllBySpecification() {
            long expectedCertificateId = 1;
            List<TagDTO> tagDTOS = new ArrayList<>();
            Certificate certificate = new Certificate();
            certificate.setId(expectedCertificateId);
            List<Certificate> certificates = Stream.of(certificate).collect(Collectors.toList());
            CertificateDTO certificateDTO = new CertificateDTO();
            List<CertificateDTO> certificateDTOS = Stream.of(certificateDTO).collect(Collectors.toList());
            Specification specification = new CertificatesBySpecification(new ArrayList<>());

            when(specificationCreator.receiveSpecification("tag", "partOfName", "partOfdescription", "dateSort", "nameSort")).thenReturn(Optional.of(specification));
            when(certificateDAO.readAllBySpecification(specification)).thenReturn(certificates);
            when(tagService.findByCertificateId(expectedCertificateId)).thenReturn(tagDTOS);
            when(certificateMapper.toDto(certificate, tagDTOS)).thenReturn(certificateDTO);

            List<CertificateDTO> actual = certificatesService.readAll("tag", "partOfName", "partOfdescription", "dateSort", "nameSort");

            assertEquals(certificateDTOS, actual);
        }

        @Test()
        @DisplayName("Read all certificates from empty db")
        void readAllFromEmptyDb() {

            Specification specification = new CertificatesBySpecification(new ArrayList<>());

            when(specificationCreator.receiveSpecification("tag", "partOfName", "partOfdescription", "dateSort", "nameSort")).thenReturn(Optional.of(specification));
            when(certificateDAO.readAllBySpecification(specification)).thenReturn(new ArrayList<>());

            List<CertificateDTO> actual = certificatesService.readAll("tag", "partOfName", "partOfdescription", "dateSort", "nameSort");

            assertNull(actual);
        }
    }

    @Nested
    @SuiteDisplayName("Read()")
    class Read {
        @Test
        @DisplayName("Successful.")
        void readSuccessful() {
            int expectedCertificateId = 1;
            CertificateDTO expected = new CertificateDTO("name",
                    "description", BigDecimal.valueOf(100), 30, new ArrayList<>());


            when(certificateDAO.read(expectedCertificateId)).thenReturn(Optional.of(new Certificate()));
            when(tagService.findByCertificateId(expectedCertificateId)).thenReturn(new ArrayList<>());
            when(certificateMapper.toDto(new Certificate(), new ArrayList<>())).thenReturn(expected);

            CertificateDTO actualCertificateDto = certificatesService.read(expectedCertificateId);

            assertEquals(expected, actualCertificateDto);
        }

        @Test
        @DisplayName("Unsuccessful.")
        void readUnsuccessful() {
            int expectedCertificateId = 1;

            when(certificateDAO.read(expectedCertificateId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> certificatesService.read(expectedCertificateId));
        }
    }

    @Nested
    @SuiteDisplayName("Create()")
    class Create {

        @Test
        @DisplayName("Successful")
        void createSuccessful() {
            long expectedId = 1;
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(expectedId);
            List<TagDTO> tagDTOs = Stream.of(tagDTO).collect(Collectors.toList());
            CertificateDTO certificateDTO = new CertificateDTO();
            certificateDTO.setTags(tagDTOs);
            Certificate certificate = new Certificate();
            certificate.setId(expectedId);

            when(certificateMapper.toEntity(certificateDTO)).thenReturn(certificate);
            when(certificateDAO.create(certificate)).thenReturn(Optional.of(certificate));
            when(tagService.create(tagDTO)).thenReturn(tagDTO);
            when(tagService.findByCertificateId(expectedId)).thenReturn(tagDTOs);
            when(certificateMapper.toDto(certificate, tagDTOs)).thenReturn(certificateDTO);

            CertificateDTO actual = certificatesService.create(certificateDTO);

            assertEquals(certificateDTO, actual);
            verify(certificateTagService).add(expectedId, expectedId);
        }

        @Test
        @DisplayName("Unsuccessful")
        void createUnsuccessful() {
            CertificateDTO certificateDTO = new CertificateDTO();
            Certificate certificate = new Certificate();

            when(certificateMapper.toEntity(certificateDTO)).thenReturn(certificate);
            when(certificateDAO.create(certificate)).thenReturn(Optional.empty());

            assertThrows(EntityNotAddedException.class, () -> certificatesService.create(certificateDTO));
        }
    }

    @Nested
    @SuiteDisplayName("Update()")
    class Update {

        @Test
        void updateSuccessful() {
            long expectedId = 1;
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(expectedId);
            List<TagDTO> tagDTOs = Stream.of(tagDTO).collect(Collectors.toList());
            CertificateDTO certificateDTO = new CertificateDTO();
            certificateDTO.setTags(tagDTOs);
            Certificate certificate = new Certificate();
            certificate.setId(expectedId);

            when(certificateMapper.toEntity(certificateDTO)).thenReturn(certificate);
            when(certificateDAO.update(certificate)).thenReturn(Optional.of(certificate));
            when(tagService.create(tagDTO)).thenReturn(tagDTO);
            when(tagService.findByCertificateId(expectedId)).thenReturn(tagDTOs);
            when(certificateMapper.toDto(certificate, tagDTOs)).thenReturn(certificateDTO);

            CertificateDTO actual = certificatesService.update(expectedId, certificateDTO);

            assertEquals(certificateDTO, actual);
            verify(certificateTagService).add(expectedId, expectedId);
        }

        @Test
        void updateUnsuccessful() {
            long expectedId=1;
            CertificateDTO certificateDTO = new CertificateDTO();
            Certificate certificate = new Certificate();

            when(certificateMapper.toEntity(certificateDTO)).thenReturn(certificate);
            when(certificateDAO.update(certificate)).thenReturn(Optional.empty());

            assertThrows(EntityNotUpdatedException.class, ()->certificatesService.update(expectedId,certificateDTO));
        }
    }

    @Nested
    @SuiteDisplayName("Delete()")
    class Delete {
        @Test
        void deleteSuccessful() {
            long expectedId =1;
            List<TagDTO> tagDTOS = new ArrayList<>();
            Certificate certificate = new Certificate();
            certificate.setId(expectedId);
            CertificateDTO certificateDTO = new CertificateDTO();
            certificateDTO.setId(expectedId);

            when(tagService.findByCertificateId(expectedId)).thenReturn(tagDTOS);
            when(certificateDAO.delete(expectedId)).thenReturn(Optional.of(certificate));
            when(certificateMapper.toDto(certificate,tagDTOS)).thenReturn(certificateDTO);

            CertificateDTO actual = certificatesService.delete(expectedId);

            assertEquals(certificateDTO, actual);
            verify(certificateTagService).deleteByCertificateId(expectedId);
        }

        @Test
        void deleteUnsuccessful() {
            long expectedId =1;
            List<TagDTO> tagDTOS = new ArrayList<>();

            when(tagService.findByCertificateId(expectedId)).thenReturn(tagDTOS);
            when(certificateDAO.delete(expectedId)).thenReturn(Optional.empty());

            assertThrows(EntityNotDeletedException.class,()->certificatesService.delete(expectedId));
            verify(certificateTagService).deleteByCertificateId(expectedId);
        }
    }
}