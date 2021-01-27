package com.epam.esm.repository.specification.impl;

import com.epam.esm.dto.DataSortOrder;
import com.epam.esm.repository.specification.Specification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CertificatesSortByDateSpecification implements Specification {

    private final String sqlRequest;
    private final DataSortOrder dataSortOrder;


    @Override
    public String toSqlRequest() {
        return sqlRequest + "certificates.create_date " + dataSortOrder.name();
    }

    @Override
    public Object[] receiveParameters() {
        return new Object[]{};
    }
}
