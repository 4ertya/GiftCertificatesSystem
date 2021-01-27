package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping()
    public List<TagDTO> readAll() {
        return tagService.findAllTags();
    }

    @GetMapping("/{id}")
    public TagDTO read(@PathVariable("id") int id) {
        return tagService.findTagById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagDTO create(@Validated @RequestBody TagDTO tagDTO) {
        return tagService.createTag(tagDTO);
    }

    @PatchMapping("/{id}")
    public TagDTO update(@PathVariable("id") long id,@RequestBody TagDTO tagDTO) {
        tagDTO.setId(id);
        return tagService.updateTag(tagDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        tagService.deleteTag(id);
    }


}
