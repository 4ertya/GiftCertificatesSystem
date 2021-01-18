package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.validator.NewEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @GetMapping(produces = {"application/json"})
    public List<CertificateDTO> readAll(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String certificateName,
            @RequestParam(required = false) String description
    ) {
        return certificatesService.readAll(tagName, certificateName, description);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public CertificateDTO read(@PathVariable("id") int id) {
        return certificatesService.read(id);
    }

    @PostMapping(value = "/new", produces = {"application/json"}, consumes = {"application/json"})
    public CertificateDTO create(@Validated(NewEntity.class) @RequestBody CertificateDTO certificateDTO) {
        System.out.println(certificateDTO.getName());
        return certificatesService.create(certificateDTO);
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public CertificateDTO update(@PathVariable("id") int id, @Valid @RequestBody CertificateDTO certificateDTO) {
        return certificatesService.update(id, certificateDTO);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public CertificateDTO delete(@PathVariable("id") int id) {
        return certificatesService.delete(id);
    }

}
