package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.SubTag;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagResponse {
  Long id;
  String tagName;
  Timestamp createAt;
  Timestamp updateAt;
  List<SubTagResponse> subTagResponses;
}