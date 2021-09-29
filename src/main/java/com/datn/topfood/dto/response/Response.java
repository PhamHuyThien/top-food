package com.datn.topfood.dto.response;

import com.datn.topfood.util.constant.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response<T> {
    private boolean status;
    private String message;
    private T data;

    public Response(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(T data) {
        this.status = true;
        this.message = Message.OTHER_SUCCESS;
        this.data = data;
    }
}
