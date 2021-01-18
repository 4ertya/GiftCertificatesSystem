package com.epam.esm.dto;

import com.epam.esm.validator.NewEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TagDTO {
    private Integer id;
    @NotBlank(groups = {NewEntity.class})
    @Size(min = 3, max = 10)
    private String name;

}
