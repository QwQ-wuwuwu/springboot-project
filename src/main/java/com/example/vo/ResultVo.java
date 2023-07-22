package com.example.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResultVo {
    private Integer code;
    private String message;
    private Map<String,Object> data;
    public static ResultVo success(Map<String,Object> data,String message) {
        return ResultVo.builder().code(666).data(data).message(message).build();
    }
    public static ResultVo success(int code, Map<String, Object> data) {
        return ResultVo.builder().code(code).data(data).build();
    }
    public static ResultVo error(int code,String message) {
        return ResultVo.builder().code(code).message(message).build();
    }
    public static ResultVo error(int code) {
        return ResultVo.builder().code(code).build();
    }
}
