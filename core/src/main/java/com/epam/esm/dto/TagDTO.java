package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TagDTO {
    private Integer id;
    @NotBlank(message = "Need to enter a name")
    @Size(min = 3, max = 10, message = "Name length must be between 3 and 20 characters")
    private String name;

}
