package com.datn.topfood.dto.response;

import com.datn.topfood.data.model.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalIdCache;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendMessageResponse {
    Messages messages;
}
