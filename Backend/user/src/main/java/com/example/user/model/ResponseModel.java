package com.example.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
    private Object payload;
    private String message;
    private String error;

    public void payloadConstruction(Object payload, String message, String error) {
        this.payload = payload;
        this.message = message;
        this.error = error;
    }
}
