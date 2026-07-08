package com.week6.AuthFlow.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    private T data;
    private LocalDateTime timeStamp;
    private APIError error;

    public APIResponse(T data) {
        this.data = data;
        this.error = null;
        this.timeStamp = LocalDateTime.now();
    }

    public APIResponse(APIError error) {
        this.data = null;
        this.error = error;
        this.timeStamp = LocalDateTime.now();
    }
}
