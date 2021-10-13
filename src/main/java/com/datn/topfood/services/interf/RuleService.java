package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.request.RuleRequest;
import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.dto.response.RuleResponse;


public interface RuleService {
  RuleResponse createRule(RuleRequest request);
  RuleResponse updateRule(RuleRequest request,Long id);
  PageResponse<RuleResponse> searchByRuleTitle(String title, PageRequest request);
  RuleResponse findById(Long id);
}
