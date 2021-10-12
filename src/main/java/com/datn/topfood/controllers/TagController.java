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

@RestController
@RequestMapping("/api/tag")
public class TagController {
  @Autowired
  TagService tagService;
  @PostMapping(path = "")
  public TagResponse createTag(@RequestBody TagRequest create){
    return tagService.createTag(create);
  }
  @GetMapping("/{id}")
  public TagResponse getTagById(@PathVariable Long id){
    return tagService.findById(id);
  }
  @PutMapping("/{id}")
  public TagResponse updateTag(@RequestBody TagRequest request ,@PathVariable Long id){
    return tagService.updateTag(request,id);
  }
  @GetMapping("")
  public PageResponse<TitleTagResponse> searchByTagName(@RequestParam String tagName, PageRequest pageRequest){
    return tagService.searchByTagName(tagName,pageRequest);
  }

}
