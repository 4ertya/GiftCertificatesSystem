package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.validator.NewEntity;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @GetMapping
    public List<CertificateDTO> readAll(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String dateSort,
            @RequestParam(required = false) String nameSort
    ) {
        return certificatesService.readAll(tag, name, description, dateSort, nameSort);
    }

    @GetMapping("/{id}")
    public CertificateDTO read(@PathVariable("id") long id) {
        return certificatesService.read(id);
    }

    @PostMapping
    public CertificateDTO create(@Validated(NewEntity.class) @RequestBody CertificateDTO certificateDTO) {
        return certificatesService.create(certificateDTO);
    }

    @PatchMapping("/{id}")
    public CertificateDTO update(@PathVariable("id") long id, @Valid @RequestBody CertificateDTO certificateDTO) {
        certificateDTO.setId(id);
        return certificatesService.update(certificateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        certificatesService.delete(id);
    }

}
