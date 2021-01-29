package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;
import lombok.AllArgsConstructor;


public class CertificatesByPartNameSpecification implements Specification {
    private final String partOfName;

    public CertificatesByPartNameSpecification(String partOfName) {
        this.partOfName = "%"+partOfName+"%";
    }

    @Override
    public String toSqlRequest() {
        return " AND certificates.name LIKE ?";
    }

    @Override
    public Object[] receiveParameters() {
        return new Object[]{partOfName};
    }
}
