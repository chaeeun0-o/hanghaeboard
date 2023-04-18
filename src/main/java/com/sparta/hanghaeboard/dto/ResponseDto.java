package com.sparta.hanghaeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class ResponseDto{
    private boolean success;
    private Object data;


    public ResponseDto(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
}
