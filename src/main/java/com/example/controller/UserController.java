package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/teachers")
    public ResultVo listTeacher() {
        List<User> users = userService.listTeacher();
        if (users.size() == 0) {
            return ResultVo.builder()
                    .code(666)
                    .message("目前老师未加入")
                    .data(null)
                    .build();
        }
        return ResultVo.success(666,Map.of("teachers",users));
    }
    @GetMapping("/select")
    public ResultVo getInfo(@RequestAttribute("uid") long uid, @RequestHeader("role") int role) {
        System.out.println(uid);
        System.out.println(role);
        return ResultVo.success(666,null);
    }
}
