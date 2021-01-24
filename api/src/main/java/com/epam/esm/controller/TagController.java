package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping()
    public List<TagDTO> readAll() {
        return tagService.readAllTags();
    }

    @GetMapping("/{id}")
    public TagDTO read(@PathVariable("id") int id) {
        return tagService.read(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/new")
    public TagDTO create(@Validated @RequestBody TagDTO tagDTO) {
        return tagService.create(tagDTO);
    }

    @PatchMapping("/{id}")
    public TagDTO update(@PathVariable("id") long id,@RequestBody TagDTO tagDTO) {
        tagDTO.setId(id);
        return tagService.update(tagDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        tagService.delete(id);
    }


}
