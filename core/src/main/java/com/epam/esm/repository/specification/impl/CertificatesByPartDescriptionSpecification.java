package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

public class CertificatesByPartDescriptionSpecification implements Specification {
    private final String partOfDescription;

    public CertificatesByPartDescriptionSpecification(String partOfDescription) {
        this.partOfDescription = "%" + partOfDescription + "%";
    }

    @Override
    public String toSqlRequest() {
        return " AND certificates.description LIKE ?";
    }

    @Override
    public Object[] receiveParameters() {
        return new Object[]{partOfDescription};
    }
}
