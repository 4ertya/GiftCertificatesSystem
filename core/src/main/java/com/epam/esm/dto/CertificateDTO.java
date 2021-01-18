package com.epam.esm.dto;


import com.epam.esm.validator.NewEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
public class CertificateDTO {
    @Null
    private Integer id;
    @NotBlank(groups = {NewEntity.class})
    @Size(min = 3, max = 20)
    private String name;
    @NotBlank(groups = {NewEntity.class})
    @Size(min = 3, max = 45, groups = {NewEntity.class})
    private String description;
    @NotNull(groups = {NewEntity.class})
    @Min(value = 1,groups = {NewEntity.class})
    private Integer price;
    @NotNull(groups = {NewEntity.class})
    @Min(value = 1,groups = {NewEntity.class})
    private Integer duration;
    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    @Valid
    @NotEmpty(groups = {NewEntity.class})
    private List<TagDTO> tags;

    public CertificateDTO(String name, String description, Integer price, Integer duration, List<TagDTO> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}
