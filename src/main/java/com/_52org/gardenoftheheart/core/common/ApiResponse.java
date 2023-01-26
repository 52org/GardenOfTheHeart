package com._52org.gardenoftheheart.core.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse {

    private String message;

    private Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Object data) {
        this("Success", data);
    }

    public static ApiResponse noContent() {
        return new ApiResponse("");
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(data);
    }

    public static ApiResponse fail(String message) {
        return new ApiResponse(message, "");
    }

}
