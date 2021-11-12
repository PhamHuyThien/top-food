package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Base;
import com.datn.topfood.util.enums.ReactType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReactionResponse extends Base {
    ReactType type;
    ProfileResponse profile;
}
