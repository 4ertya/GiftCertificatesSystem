package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TagDTO {
    private Integer id;
    @NotNull(message = "Need to enter a name")
    @Size(min = 3, max = 10, message = "Name length must be between 3 and 20 characters")
    private String name;

}
