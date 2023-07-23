package com.example.controller;

import com.example.component.JWTComponent;
import com.example.config.PasswordEncodeConfig;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.vo.ResultVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private PasswordEncodeConfig passwordEncodeConfig;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTComponent jwtComponent;
    @PostMapping("/login")
    public ResultVo login(@RequestBody User user, HttpServletResponse response) {
        User u = userRepository.findByName(user.getName());
        if (user == null || !passwordEncodeConfig.passwordEncoder().matches(user.getPassword(), u.getPassword())) {
            return ResultVo.builder()
                    .code(400)
                    .message("账号或密码错误")
                    .build();
        }
        String code = switch (u.getRole()) {
            case User.ROLE_STUDENT -> "n28oej2";
            case User.ROLE_TEACHER -> "io2io72";
            case User.ROLE_ADMIN -> "ao2pjk4";
            default -> "";
        };
        //登录成功后信息将会被添加到响应头中，供前端或客服端使用
        String token = jwtComponent.encode(Map.of("uid",u.getId(),"role",u.getRole()));
        response.addHeader("role",code);
        response.addHeader("token",token);
        if (code.equals("n28oej2")) {
            return ResultVo.success(Map.of("name", u.getName(),
                            "role",code,
                            "SelectTeacher",u.getTeacherName(),
                    "SelectTime",u.getSelectTime()
                            ),
                    "登录成功");
        }
        if (code.equals("io2io72")) {
            return ResultVo.success(Map.of("name", u.getName(),
                            "role",code,
                    "SelectedCount",u.getCount()),
                    "登录成功");
        }
        return ResultVo.success(Map.of("name", u.getName(),
                        "role",code),
                "登录成功");
    }
}
