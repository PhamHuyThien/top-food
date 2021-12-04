package com.datn.topfood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalAccountStore {
    Long total;
    Long storeActive;
    Long storeBlock;
    Long storeWaitActive;
}
