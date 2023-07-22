package com.example.aop;

import com.example.entity.StartTime;
import com.example.exception.XException;
import com.example.vo.Code;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect//使用aop对功能进行控制
public class StudentAspect {
    @Autowired
    private StartTime time;
    @Around("execution(* com.example.service.UserService.*(..))")
    public Object checkTime(ProceedingJoinPoint joinPoint) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isBefore(time.getStartTime()) || localDateTime.isAfter(time.getEndTime())) {
            throw new XException(400,"不在系统开放时间内");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new XException(Code.TOKEN_EXPIRED,"请求异常");
        }
    }
}
