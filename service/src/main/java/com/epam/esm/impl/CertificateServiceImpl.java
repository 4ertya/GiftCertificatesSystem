package com.epam.esm.impl;

import com.epam.esm.Certificate;
import com.epam.esm.CertificateDAO;
import com.epam.esm.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificatesService {

    private final CertificateDAO certificateDAO;

    @Autowired
    public CertificateServiceImpl(@Qualifier("SQL") CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    public List<Certificate> readAll() {
        return certificateDAO.readAll();
    }

    @Override
    public Certificate read(int id) {
        return certificateDAO.read(id);
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificateDAO.create(certificate);
        return null;
    }
}
