package com.datn.topfood.configs;

import com.datn.topfood.dto.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<?>> handleAll(final Exception ex) {
        String message = ex.getMessage();
        if (StringUtils.isAllEmpty(message)) {
            message = ExceptionUtils.getStackTrace(ex);
        }
        Response<?> apiError = new Response<>(message);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Response<?>> handleResponseStatusException(final ResponseStatusException ex) {
        String message = ex.getMessage();
        String[] messages = Objects.requireNonNull(message).split("\"");
        if (messages.length > 1 && StringUtils.isNotEmpty(messages[1])) {
            message = messages[1];
        }
        Response<?> apiError = new Response<>(message);
        return new ResponseEntity<>(apiError, ex.getStatus());
    }
}