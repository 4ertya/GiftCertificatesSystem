package com.epam.esm.dto;

import com.epam.esm.validator.NewEntity;
import com.epam.esm.validator.UpdateEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private Long id;
    @NotBlank(groups = {NewEntity.class, UpdateEntity.class})
    @Size(min = 3, max = 10,groups = {NewEntity.class, UpdateEntity.class})
    private String name;
}
