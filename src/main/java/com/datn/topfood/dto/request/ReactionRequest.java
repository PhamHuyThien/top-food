package com.datn.topfood.dto.request;

import com.datn.topfood.util.enums.ReactType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReactionRequest {
    ReactType type;
}
