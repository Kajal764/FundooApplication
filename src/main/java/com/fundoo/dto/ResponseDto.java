package com.fundoo.dto;

import lombok.ToString;

@ToString
public class ResponseDto {
    public final String message;
    public final int statusCode;

    public ResponseDto(String message, int statusCode) {
       this.message=message;
       this.statusCode=statusCode;
    }
}
