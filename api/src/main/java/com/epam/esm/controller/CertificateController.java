package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.DataSortOrder;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.NewEntity;
import com.epam.esm.validator.UpdateEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/certificates")
@Validated
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping
    public List<CertificateDTO> readAll(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) DataSortOrder dateSort,
            @RequestParam(required = false) DataSortOrder nameSort
    ) {
        return certificateService.findAllCertificates(tag, name, description, dateSort, nameSort);
    }

    @GetMapping("/{id}")
    public CertificateDTO read(@PathVariable("id") @Valid @Min(1) long id) {
        return certificateService.findCertificateById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CertificateDTO create(@Validated(NewEntity.class) @RequestBody CertificateDTO certificateDTO) {
        return certificateService.createCertificate(certificateDTO);
    }

    @PatchMapping("/{id}")
    public CertificateDTO update(@PathVariable("id") long id, @Validated(UpdateEntity.class) @RequestBody CertificateDTO certificateDTO) {
        certificateDTO.setId(id);
        return certificateService.updateCertificate(certificateDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        certificateService.deleteCertificate(id);
    }


}
