package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CertificatesByTagSpecification implements Specification {

    private final String tagName;

    @Override
    public String toSqlRequest() {
        return " AND tags.name = ?";
    }

    @Override
    public Object[] receiveParameters() {
        return new Object[]{tagName};
    }
}
