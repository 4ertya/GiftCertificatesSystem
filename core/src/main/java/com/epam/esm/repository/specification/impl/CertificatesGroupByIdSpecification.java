package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;

public class CertificatesGroupByIdSpecification implements Specification {
    @Override
    public String toSqlRequest() {
        return " GROUP BY certificates.id ";
    }

    @Override
    public Object[] receiveParameters() {
        return new Object[]{};
    }
}
