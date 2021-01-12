package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class TagDTO {
    private Integer id;
    @NotNull(message = "Need to enter a name")
    @Size(min = 3, max = 10, message = "Name length must be between 3 and 20 characters")
    private String name;

    public TagDTO(String name) {
        this.name = name;
    }
}
