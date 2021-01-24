package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum DataSortType {
    ASC("ASC"),
    DESC("DESC");

    private final String value;

    public static DataSortType getDataSortType(String requestSort){
        return Stream.of(values())
                .filter(type -> type.getValue().equalsIgnoreCase(requestSort))
                .findAny().orElse(null);
    }
}
