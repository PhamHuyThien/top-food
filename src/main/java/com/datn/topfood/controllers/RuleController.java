package com.datn.topfood.controllers;

import com.datn.topfood.dto.request.RuleRequest;
import com.datn.topfood.dto.response.RuleResponse;
import com.datn.topfood.services.interf.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rule")
public class RuleController {
  @Autowired
  RuleService ruleService;
  @GetMapping(path = "")
  public List<RuleResponse> getAllRule(){
    return ruleService.getAllRule();
  }
  @GetMapping("/{id}")
  public RuleResponse findById(@PathVariable Long id){
    return ruleService.findById(id);
  }
  @PostMapping(path = "")
  public RuleResponse createRule(@RequestBody RuleRequest request){
    return ruleService.createRule(request);
  }
  @PutMapping("/{id}")
  public RuleResponse updateRule(@RequestBody RuleRequest request, @PathVariable Long id){
    return ruleService.updateRule(request, id);
  }
}
