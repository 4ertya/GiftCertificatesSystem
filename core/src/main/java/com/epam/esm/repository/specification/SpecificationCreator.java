package com.epam.esm.repository.specification;

import com.epam.esm.dto.DataSortType;
import com.epam.esm.repository.specification.impl.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecificationCreator {
    private final static String ORDER_BY = " ORDER BY ";
    private final static String DELIMITER = ", ";

    public Optional<Specification> receiveSpecification(String tagName, String partOfName, String partOfDescription, String dateSort, String nameSort) {
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

        if (DataSortType.getDataSortType(dateSort) != null) {
            specifications.add(new CertificatesSortByDateSpecification(ORDER_BY, DataSortType.getDataSortType(dateSort)));
            isTwoSort = true;
        }

        if (DataSortType.getDataSortType(nameSort) != null) {
            specifications.add(new CertificatesSortByNameSpecification(isTwoSort ? DELIMITER : ORDER_BY, DataSortType.getDataSortType(nameSort)));
        }

        if (specifications.size() == 1) {
            return Optional.empty();
        }

        return Optional.of(new CertificatesBySpecification(specifications));
    }
}
