package com.example.interceptor;

import com.example.exception.XException;
import com.example.vo.Code;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component//对教师权限进行验证
public class TeacherInterceptor implements HandlerInterceptor {
    private final List<String> includes = List.of("/api/teacher");
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();
        for (String p : includes) {
            if (requestPath.startsWith(p)) {
                int role = (int) request.getAttribute("role");
                if (role == 0) {
                    // 返回无权限的错误信息
                    throw new XException(Code.UNAUTHORIZED,"无教师权限权限");
                }
            }
        }
        return true;
    }
}
