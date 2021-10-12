package com.datn.topfood.dto.messages;

import com.datn.topfood.dto.response.PageResponse;
import com.datn.topfood.util.enums.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class PageMessageResponse<T> extends PageResponse<T> {
    MessageType type;

    public PageMessageResponse(long total, long totalElements, long pageSize, long pageTotal, MessageType type) {
        super(total, totalElements, pageSize, pageTotal);
        this.type = type;
    }

    public PageMessageResponse(MessageType type) {
        this.type = type;
    }

    public PageMessageResponse(List<T> data, long totalElements, long pageSize, MessageType type) {
        super(data, totalElements, pageSize);
        this.type = type;
    }
}
