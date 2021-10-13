package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.SubTagRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.SubTagResponse;
import com.datn.topfood.services.interf.SubTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subtag")
public class SubTagController {
  @Autowired
  SubTagService subTagService;
  @PostMapping(path = "")
  public SubTagResponse createSubTag(@RequestBody SubTagRequest request){
    return subTagService.createSubTag(request);
  }
  @PutMapping("/{id}")
  public SubTagResponse updateSubTag(@RequestBody SubTagRequest request,@PathVariable("id") Long id){
    return subTagService.updateSubTag(request, id);
  }
  @GetMapping("")
  public PageResponse<SubTagResponse> searchBySubTagName(@RequestParam String subTagName, PageRequest request){
    return subTagService.searchBySubTagName(subTagName,request);
  }
  @GetMapping("/{id}")
  public SubTagResponse findByIdSubTag(@PathVariable("id") Long id){
    return subTagService.findById(id);
  }
  @DeleteMapping("/{id}")
  public void deleteSubTag(@PathVariable("id") Long id){
    subTagService.deleteSubTag(id);
  }

}
