package com.epam.esm.repository.specification;

import com.epam.esm.repository.specification.impl.CertificatesByPartDescriptionSpecification;
import com.epam.esm.repository.specification.impl.CertificatesByPartNameSpecification;
import com.epam.esm.repository.specification.impl.CertificatesBySpecification;
import com.epam.esm.repository.specification.impl.CertificatesByTagSpecification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecificationCreator {
    public Optional<Specification> receiveSpecification(String tagName, String partOfName, String partOfDescription){
        List<Specification> specifications = new ArrayList<>();

        if (tagName!=null){
            specifications.add(new CertificatesByTagSpecification(tagName));
        }

        if (partOfName!=null){
            specifications.add(new CertificatesByPartNameSpecification(partOfName));
        }

        if (partOfDescription!=null){
            specifications.add(new CertificatesByPartDescriptionSpecification(partOfDescription));
        }

        return Optional.of(new CertificatesBySpecification(specifications));
    }
}
