package com.datn.topfood.dto.messages;

import com.datn.topfood.dto.response.Response;
import com.datn.topfood.util.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class MessageResponse<T> extends Response<T> {
    MessageType type;

    public MessageResponse(boolean status, String message, T data, MessageType type) {
        super(status, message, data);
        this.type = type;
    }

    public MessageResponse(MessageType type) {
        this.type = type;
    }

    public MessageResponse(boolean status, String message, MessageType type) {
        super(status, message);
        this.type = type;
    }

    public MessageResponse(String message, MessageType type) {
        super(message);
        this.type = type;
    }

    public MessageResponse(T data, MessageType type) {
        super(data);
        this.type = type;
    }
}
