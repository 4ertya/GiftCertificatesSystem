package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.Specification;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CertificatesBySpecification implements Specification {

    private final List<Specification> specificationList;

    @Override
    public String toSqlRequest() {
        return specificationList.stream()
                .map(Specification::toSqlRequest)
                .collect(Collectors.joining());
    }

    @Override
    public Object[] receiveParameters() {
        return specificationList.stream()
                .flatMap(x -> Arrays.stream(x.receiveParameters()))
                .toArray();

    }
}
