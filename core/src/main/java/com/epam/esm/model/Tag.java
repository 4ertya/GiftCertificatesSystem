package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
