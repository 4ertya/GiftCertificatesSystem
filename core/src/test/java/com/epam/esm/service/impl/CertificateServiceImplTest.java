package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.DataSortOrder;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.specification.Specification;
import com.epam.esm.repository.specification.SpecificationCreator;
import com.epam.esm.repository.specification.impl.CertificatesBySpecification;
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
    private CertificateRepository certificateRepository;
    @Mock
    private TagService tagService;
    @Mock
    CertificateMapper certificateMapper;
    @Mock
    SpecificationCreator specificationCreator;


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

            when(certificateRepository.findAllCertificates()).thenReturn(certificates);
            when(tagService.findTagByCertificateId(expectedCertificateId)).thenReturn(tagDTOS);
            when(certificateMapper.toDto(certificate, tagDTOS)).thenReturn(certificateDTO);

            List<CertificateDTO> actual = certificatesService.findAllCertificates(null, null, null, null, null);

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

            when(specificationCreator.receiveSpecification("tag", "partOfName", "partOfdescription", DataSortOrder.ASC, DataSortOrder.ASC)).thenReturn(Optional.of(specification));
            when(certificateRepository.findAllCertificatesBySpecification(specification)).thenReturn(certificates);
            when(tagService.findTagByCertificateId(expectedCertificateId)).thenReturn(tagDTOS);
            when(certificateMapper.toDto(certificate, tagDTOS)).thenReturn(certificateDTO);

            List<CertificateDTO> actual = certificatesService.findAllCertificates("tag", "partOfName", "partOfdescription", DataSortOrder.ASC, DataSortOrder.ASC);

            assertEquals(certificateDTOS, actual);
        }

        @Test()
        @DisplayName("Read all certificates from empty db")
        void readAllFromEmptyDb() {

            Specification specification = new CertificatesBySpecification(new ArrayList<>());

            when(specificationCreator.receiveSpecification("tag", "partOfName", "partOfdescription", DataSortOrder.ASC, DataSortOrder.ASC)).thenReturn(Optional.of(specification));
            when(certificateRepository.findAllCertificatesBySpecification(specification)).thenReturn(new ArrayList<>());

            List<CertificateDTO> actual = certificatesService.findAllCertificates("tag", "partOfName", "partOfdescription", DataSortOrder.ASC, DataSortOrder.ASC);

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


            when(certificateRepository.findCertificateById(expectedCertificateId)).thenReturn(Optional.of(new Certificate()));
            when(tagService.findTagByCertificateId(expectedCertificateId)).thenReturn(new ArrayList<>());
            when(certificateMapper.toDto(new Certificate(), new ArrayList<>())).thenReturn(expected);

            CertificateDTO actualCertificateDto = certificatesService.findCertificateById(expectedCertificateId);

            assertEquals(expected, actualCertificateDto);
        }

        @Test
        @DisplayName("Unsuccessful.")
        void readUnsuccessful() {
            int expectedCertificateId = 1;

            when(certificateRepository.findCertificateById(expectedCertificateId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> certificatesService.findCertificateById(expectedCertificateId));
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
            when(certificateRepository.createCertificate(certificate)).thenReturn(certificate);
            when(tagService.createTag(tagDTO)).thenReturn(tagDTO);
            when(tagService.findTagByCertificateId(expectedId)).thenReturn(tagDTOs);
            when(certificateRepository.findCertificateById(expectedId)).thenReturn(Optional.of(certificate));
            when(certificateMapper.toDto(certificate, tagDTOs)).thenReturn(certificateDTO);

            CertificateDTO actual = certificatesService.createCertificate(certificateDTO);

            assertEquals(certificateDTO, actual);
            verify(tagService).bind(expectedId, expectedId);
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
            certificateDTO.setId(expectedId);
            certificateDTO.setTags(tagDTOs);
            Certificate certificate = new Certificate();
            certificate.setId(expectedId);
            when(certificateRepository.findCertificateById(expectedId)).thenReturn(Optional.of(certificate));
            when(certificateMapper.toEntity(certificateDTO)).thenReturn(certificate);
            when(tagService.createTag(tagDTO)).thenReturn(tagDTO);
            when(tagService.findTagByCertificateId(expectedId)).thenReturn(tagDTOs);
            when(certificateMapper.toDto(certificate, tagDTOs)).thenReturn(certificateDTO);

            CertificateDTO actual = certificatesService.updateCertificate(certificateDTO);

            assertEquals(certificateDTO, actual);
            verify(tagService).bind(expectedId, expectedId);
            verify(certificateRepository).updateCertificate(certificate);
        }

        @Test
        void updateUnsuccessful() {
            long expectedId=1;
            CertificateDTO certificateDTO = new CertificateDTO();
            certificateDTO.setId(expectedId);

            when(certificateRepository.findCertificateById(expectedId)).thenThrow(new EntityNotFoundException());
            assertThrows(EntityNotFoundException.class, ()->certificatesService.updateCertificate(certificateDTO));
        }
    }

    @Nested
    @SuiteDisplayName("Delete()")
    class Delete {
        @Test
        void deleteSuccessful() {
            long expectedId =1;
            Certificate certificate = new Certificate();
            certificate.setId(expectedId);
            CertificateDTO certificateDTO = new CertificateDTO();
            certificateDTO.setId(expectedId);
            when(certificateRepository.findCertificateById(expectedId)).thenReturn(Optional.of(certificate));
            certificatesService.deleteCertificate(expectedId);
            verify(tagService).unbindByCertificateId(expectedId);
            verify(certificateRepository).deleteCertificate(expectedId);
        }

        @Test
        void deleteUnsuccessful() {
            long expectedId =1;

            when(certificateRepository.findCertificateById(expectedId)).thenThrow(new EntityNotFoundException());

            assertThrows(EntityNotFoundException.class,()->certificatesService.deleteCertificate(expectedId));
        }
    }
}