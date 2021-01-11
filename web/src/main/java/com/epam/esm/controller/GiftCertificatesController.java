package com.epam.esm.controller;

import com.epam.esm.Certificate;
import com.epam.esm.CertificatesService;
import com.epam.esm.impl.SQLCertificatesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificatesController {

    private final CertificatesService certificatesService;

    @Autowired
    public GiftCertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public List<Certificate> readAll(Model model) {
        return certificatesService.readAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public Certificate read(@PathVariable("id") int id, Model model) {
        System.out.println(certificatesService.read(id));
        return certificatesService.read(id);
    }


    @PostMapping(value = "/new", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public Certificate newCertificate(@RequestBody Certificate certificate) {
        return certificatesService.create(certificate);
    }

    @PostMapping()
    public String create(@ModelAttribute("certificate") Certificate certificate) {
        return "redirect:/certificates";
    }
}
