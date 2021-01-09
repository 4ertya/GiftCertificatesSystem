package com.epam.esm.controller;

import com.epam.esm.CertificatesDAO;
import com.epam.esm.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/certificates")
public class GiftCertificatesController {
    private final CertificatesDAO certificatesDAO;

    @Autowired
    public GiftCertificatesController(CertificatesDAO certificatesDAO) {
        this.certificatesDAO = certificatesDAO;
    }

    @GetMapping()
    public String readAll(Model model) {
        model.addAttribute("certificates", certificatesDAO.index());
        return "certificates/index";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable("id") int id, Model model) {
        return "certificates/show";
    }

    @GetMapping("/new")
    public String newCertificate(Model model) {
        model.addAttribute("certificate", new Certificate());
        return "certificates/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("certificate") Certificate certificate) {
        return "redirect:/certificates";
    }
}
