package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Rule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {
    String content;
    String profileName;
    List<Rule> rule;
}
