package com.fundoo.dto;

import lombok.ToString;

@ToString
public class ResponseDto {
    public String message;
    public  int statusCode;

    public ResponseDto(String message, int statusCode) {
       this.message=message;
       this.statusCode=statusCode;
    }

}
