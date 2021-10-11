package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.SubTagRequest;
import com.datn.topfood.dto.response.SubTagResponse;
import com.datn.topfood.services.interf.SubTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subtag")
public class SubTagController {
  @Autowired
  SubTagService subTagService;
  @PostMapping(path = "")
  public SubTagResponse createSubTag(@RequestBody SubTagRequest request){
    return subTagService.createSubTag(request);
  }

}
