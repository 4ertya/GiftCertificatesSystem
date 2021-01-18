package com.epam.esm.repository.specification;

public interface Specification {

    String toSqlRequest();

    Object[] receiveParameters();
}
