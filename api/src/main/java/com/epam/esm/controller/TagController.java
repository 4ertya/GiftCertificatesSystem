package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
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
        return tagService.readAll();
    }

    @GetMapping("/{id}")
    public TagDTO read(@PathVariable("id") int id) {
        return tagService.read(id);
    }

    @PostMapping(value = "/new")
    public TagDTO create(@RequestBody @Validated TagDTO tagDTO) {
        System.out.println(tagDTO);
        return tagService.create(tagDTO);
    }

    @PatchMapping("/{id}")
    public TagDTO update(@PathVariable("id") int id,@RequestBody TagDTO tagDTO) {
        return tagService.update(id, tagDTO);
    }

    @DeleteMapping("/{id}")
    public TagDTO delete(@PathVariable("id") int id) {
        return tagService.delete(id);
    }


}
