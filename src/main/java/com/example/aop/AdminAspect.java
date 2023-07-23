package com.example.aop;

import com.example.exception.XException;
import com.example.vo.Code;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//@Component
//@Aspect//通过aop对管理员权限进行控制，只是目前还没前端页面进行测试^.^不知行不行
//由于已经写了拦截器，这里的功能是多余的哈哈哈
public class AdminAspect {
   /* @Around("execution(* com.example.service.AdminService.*(..))")
    public Object checkRole(ProceedingJoinPoint joinPoint) {
        // 获取HttpServletRequest对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        int temp = Integer.parseInt(request.getHeader("role"));
        if (temp == 0 || temp == 1) {
            throw new XException(400,"无权限操作");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new XException(Code.BAD_REQUEST,e.getMessage() + "请求异常");
        }
    }*/
}
