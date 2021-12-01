package com.datn.topfood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalAccountUser {
    Long total;
    Long accountActive;
    Long accountBlock;
    Long accountWaitActive;
}
