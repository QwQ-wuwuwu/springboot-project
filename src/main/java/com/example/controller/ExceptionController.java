package com.example.controller;

import com.example.exception.XException;
import com.example.vo.ResultVo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(XException.class)
    public ResultVo xException(XException e) {
        return ResultVo.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(Exception.class)
    public ResultVo Exception(Exception e) {
        return ResultVo.error(400,"请求错误");
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResultVo handelDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return ResultVo.error(400, "唯一约束冲突！" + exception.getMessage());
    }
}
