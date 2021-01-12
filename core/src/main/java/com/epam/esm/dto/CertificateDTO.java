package com.epam.esm.dto;


import com.epam.esm.model.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
public class CertificateDTO {
    private Integer id;
    @NotNull(message = "Required field: Name")
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 20 characters")
    private String name;
    @NotNull(message = "required field: Description")
    @Size(min = 3, max = 45, message = "Name length must be between 3 and 45 characters")
    private String description;
    @NotNull(message = "required field: Price")
    @Size(min = 1, message = "Price can't be less than 1")
    private Integer price;
    @NotNull
    @Size(min = 1, message = "Duration can't be less than 1")
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    private List<Tag> tags;

    public CertificateDTO(String name, String description, Integer price, Integer duration, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}
