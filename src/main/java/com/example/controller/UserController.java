package com.example.controller;

import com.example.entity.User;
import com.example.exception.XException;
import com.example.service.UserService;
import com.example.vo.Code;
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
    //列出所有老师
    @GetMapping("/teachers")
    public ResultVo listTeacher(@RequestAttribute("uid") long uid,@RequestAttribute("role") int role) {
        List<User> users = userService.listTeacher(uid,role);
        if (users == null) {
            return ResultVo.builder()
                    .code(666)
                    .message("操作失败")
                    .data(null)
                    .build();
        }
        return ResultVo.success(666,Map.of("teachers",users));
    }
    //挑选老师，只有学生可以挑选
    @GetMapping("/select/{tid}")
    public ResultVo getInfo(@RequestAttribute("uid") long uid, @RequestAttribute("role") int role, @PathVariable long tid) {
        if (role == User.ROLE_TEACHER || role == User.ROLE_ADMIN) {
            throw new XException(Code.FORBIDDEN,"只有学生可以选择导师");
        }
        if (!userService.selectTeacher(uid,tid)) {
            return ResultVo.builder()
                    .code(500)
                    .message("操作失败（老师或学生不存在）")
                    .build();
        }
        return ResultVo.builder()
                .code(666)
                .message("选择成功")
                .build();
    }
    //修改密码
    @PutMapping("/password")
    public ResultVo updatePassword(@RequestParam("password") String password,@RequestParam("name") String name,@RequestParam("newPassword") String newPassword) {
        boolean flag = userService.updatePassword(name,password,newPassword);
        if (!flag) {
            return ResultVo.error(Code.BAD_REQUEST,"更新失败，请重试");
        }
        return ResultVo.builder()
                .code(666)
                .message("更新成功，请妥善保存密码")
                .build();
    }
}
