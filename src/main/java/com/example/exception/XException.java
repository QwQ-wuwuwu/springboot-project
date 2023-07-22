package com.example.exception;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XException extends RuntimeException{
    private Integer code;
    public XException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
