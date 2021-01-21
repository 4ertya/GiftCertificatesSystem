package com.epam.esm.dto;

import com.epam.esm.validator.NewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private Integer id;
    @NotBlank(groups = {NewEntity.class})
    @Size(min = 3, max = 10)
    private String name;

}
