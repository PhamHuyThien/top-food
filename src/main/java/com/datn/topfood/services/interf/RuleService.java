package com.datn.topfood.services.interf;

import com.datn.topfood.dto.request.RuleRequest;
import com.datn.topfood.dto.response.RuleResponse;

import java.util.List;

public interface RuleService {
  RuleResponse createRule(RuleRequest request);
  RuleResponse updateRule(RuleRequest request,Long id);
  List<RuleResponse> getAllRule();
  RuleResponse findById(Long id);
}
