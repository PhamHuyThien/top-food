package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.TagRequest;
import com.datn.topfood.dto.response.AccountResponse;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.TagResponse;
import com.datn.topfood.dto.response.TitleTagResponse;
import com.datn.topfood.services.interf.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping(path = "")
    public TagResponse createTag(@RequestBody TagRequest create) {
        return tagService.createTag(create);
    }


    @PutMapping("/{id}")
    public TagResponse updateTag(@RequestBody TagRequest request, @PathVariable Long id) {
        return tagService.updateTag(request, id);
    }

    @GetMapping("")
    public PageResponse<TitleTagResponse> searchByTagName(@RequestParam boolean enable, @RequestParam String tagName, PageRequest pageRequest) {
        return tagService.searchByTagName(enable, tagName, pageRequest);
    }

    @GetMapping("/{id}")
    public TagResponse findById(@PathVariable("id") Long id) {
        return tagService.findById(id);
    }

    @GetMapping(path = "/all")
    public List<TagResponse> getAllTag(@RequestParam String tagName) {
        return tagService.getAllTitleTag(tagName);
    }

    @DeleteMapping("{id}")
    public void deleteTagById(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
    }

    @PutMapping("/enable/{id}")
    public void updateEnable(@PathVariable("id") Long id) {
        tagService.updateEnable(id);
    }

    @GetMapping("/store/{id}")
    public TagResponse findByIdStore(@PathVariable("id") Long id) {
        return tagService.findByIdStore(id);
    }

}
