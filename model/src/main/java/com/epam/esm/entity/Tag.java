package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag {
    Integer id;
    String name;

    public Tag(String name) {
        this.name = name;
    }
}
