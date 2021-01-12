package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.model.Certificate;
import com.epam.esm.service.CertificatesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @GetMapping(produces = {"application/json"})
    public List<CertificateDTO> readAll() {
        return certificatesService.readAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public CertificateDTO read(@PathVariable("id") int id) {
        System.out.println(certificatesService.read(id));
        return certificatesService.read(id);
    }

    @PostMapping(value = "/new", produces = {"application/json"}, consumes = {"application/json"})
    public Certificate create(@RequestBody Certificate certificate) {
        return certificatesService.create(certificate);
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public Certificate update(@PathVariable("id") int id, @RequestBody Certificate certificate) {
        return certificatesService.update(id, certificate);
    }

}
