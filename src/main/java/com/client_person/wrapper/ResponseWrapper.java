package com.client_person.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ResponseWrapper<T> success(T data, String message) {
        return new ResponseWrapper<>(true, message, data);
    }

    public static <T> ResponseWrapper<T> error(String message) {
        return new ResponseWrapper<>(false, message, null);
    }
}
