package com.epam.esm.repository.specification;

import com.epam.esm.dto.DataSortType;
import com.epam.esm.repository.specification.impl.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SpecificationCreator {
    private final static String ORDER_BY = " ORDER BY ";
    private final static String DELIMITER = ", ";

    public Optional<Specification> receiveSpecification(String tagName, String partOfName, String partOfDescription, DataSortType dateSort, DataSortType nameSort) {
        List<Specification> specifications = new ArrayList<>();
        boolean isTwoSort = false;

        if (tagName != null) {
            specifications.add(new CertificatesByTagSpecification(tagName));
        }

        if (partOfName != null) {
            specifications.add(new CertificatesByPartNameSpecification(partOfName));
        }

        if (partOfDescription != null) {
            specifications.add(new CertificatesByPartDescriptionSpecification(partOfDescription));
        }

        specifications.add(new CertificatesGroupByIdSpecification());

        if (dateSort != null) {
            specifications.add(new CertificatesSortByDateSpecification(ORDER_BY, dateSort));
            isTwoSort = true;
        }

        if (nameSort != null) {
            specifications.add(new CertificatesSortByNameSpecification(isTwoSort ? DELIMITER : ORDER_BY, nameSort));
        }

        if (specifications.size() == 1) {
            return Optional.empty();
        }

        return Optional.of(new CertificatesBySpecification(specifications));
    }
}
