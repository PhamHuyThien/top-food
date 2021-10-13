package com.datn.topfood.services.service;

import com.datn.topfood.data.model.Rule;
import com.datn.topfood.data.repository.jpa.RuleRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.RuleRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.RuleResponse;
import com.datn.topfood.services.interf.RuleService;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {
  @Autowired
  RuleRepository ruleRepository;
  @Override
  public RuleResponse createRule(RuleRequest request) {
    ModelMapper mapper=new ModelMapper();
    Rule rule=mapper.map(request,Rule.class);
    rule.setCreateAt(DateUtils.currentTimestamp());
    ruleRepository.save(rule);
    return mapper.map(rule,RuleResponse.class);
  }

  @Override
  public RuleResponse updateRule(RuleRequest request,Long id) {
    Rule rule=ruleRepository.findById(id).orElseThrow(() -> new RuntimeException("profile not found" + id));
    rule.setTitle(request.getDescription());
    rule.setDescription(request.getDescription());
    rule.setUpdateAt(DateUtils.currentTimestamp());
    ruleRepository.save(rule);
    return new ModelMapper().map(rule,RuleResponse.class);
  }

  @Override
  public PageResponse<RuleResponse> searchByRuleTitle(String title, PageRequest request) {
    ModelMapper mapper = new ModelMapper();
    Pageable pageable = PageUtils.toPageable(request);
    List<RuleResponse> ruleResponses = new ArrayList<>();
    title = "%" + title + "%";
    Page<Rule> rules = ruleRepository.findByTitleLike(title, pageable);
    for (Rule x : rules) {
      RuleResponse ruleResponse = mapper.map(x, RuleResponse.class);
      ruleResponses.add(ruleResponse);
    }
    PageResponse<RuleResponse> acc = new PageResponse<>(
            ruleResponses,
            rules.getTotalElements(),
            pageable.getPageSize()
    );
    return acc;
  }


  @Override
  public RuleResponse findById(Long id) {
    Rule rule=ruleRepository.findById(id).orElseThrow(() -> new RuntimeException("rule not found"));
    return new ModelMapper().map(rule,RuleResponse.class);
  }
}
