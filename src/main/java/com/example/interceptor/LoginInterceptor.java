package com.example.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.component.JWTComponent;
import com.example.exception.XException;
import com.example.vo.Code;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component//拦截之后进行的操作
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTComponent jwtComponent;
    private final List<String> excludes = List.of("/api/login");//放行的请求
    private final List<String> includes = List.of("/api/");//拦截的请求
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();//获取请求路径
        for (String p : includes) {
            if (requestPath.startsWith(p)) {//如果请求路径是以api开头的
                boolean shouldContinue = false;
                for (String s : excludes) {
                    if (requestPath.startsWith(s)) {//如果请求路径是以api/login开头的
                        shouldContinue = true;//放行
                        break;
                    }
                }
                if (!shouldContinue) {//如果是其他路劲，进行token解密
                    String token = request.getHeader("token");
                    if (token == null) {
                        throw new XException(Code.LOGIN_ERROR,"未登录");
                    }
                    DecodedJWT decodedJWT = jwtComponent.decode(token);
                    request.setAttribute("uid",decodedJWT.getClaim("uid").asLong());
                    request.setAttribute("role",decodedJWT.getClaim("role").asInt());
                }
                break;
            }
        }
        return true;
    }
}
