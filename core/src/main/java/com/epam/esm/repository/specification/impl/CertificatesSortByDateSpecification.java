package com.epam.esm.repository.specification.impl;

import com.epam.esm.dto.DataSortType;
import com.epam.esm.repository.specification.Specification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CertificatesSortByDateSpecification implements Specification {

    private final String sqlRequest;
    private final DataSortType dataSortType;


    @Override
    public String toSqlRequest() {
        return sqlRequest + "certificates.create_date " + dataSortType.name();
    }

    @Override
    public Object[] receiveParameters() {
        return new Object[]{};
    }
}
