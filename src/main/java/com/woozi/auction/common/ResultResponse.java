package com.woozi.auction.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse<T> {

    private String status;
    private String message;
    private T data;

    public static <T> ResultResponse<T> success(String message, T data) {
        return new ResultResponse<>("success", message, data);
    }

    public static <T> ResultResponse<T> error(String message) {
        return new ResultResponse<>("error", message, null);
    }
}
