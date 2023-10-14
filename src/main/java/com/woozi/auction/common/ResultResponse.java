package com.woozi.auction.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse<T> {

    private int code;
    private String status;
    private String message;
    private T data;

    public static <T> ResultResponse<T> success(int code, String message, T data) {
        return new ResultResponse<>(code, "success", message, data);
    }

    public static <T> ResultResponse<T> error(int code, String message) {
        return new ResultResponse<>(code, "error", message, null);
    }
}
