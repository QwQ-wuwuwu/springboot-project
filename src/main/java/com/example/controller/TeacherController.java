package com.example.controller;

import com.example.entity.User;
import com.example.exception.XException;
import com.example.service.TeacherService;
import com.example.vo.Code;
import com.example.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    //查找没有选导师的同学列表
    @GetMapping("/students1")
    public ResultVo listUnSelect(@RequestAttribute("role") int role) {
        if (role == User.ROLE_STUDENT || role == User.ROLE_ADMIN) {
            throw new XException(Code.FORBIDDEN,"无权限");
        }
        else if (teacherService.listUnselect().size() == 0) {
            return ResultVo.builder()
                    .code(666)
                    .message("全部学生都已选择导师")
                    .build();
        }
        else if (teacherService.listUnselect() == null) {
            throw new XException(Code.UNAUTHORIZED,"操作失败");
        }
        return ResultVo.success(666, Map.of("UnSelect",teacherService.listUnselect()));
    }
    //查找已选某个老师的所有学生
    @GetMapping("/students2")
    public ResultVo listSelect(@RequestParam("name") String name,@RequestAttribute("role") int role) {
        if (role == User.ROLE_STUDENT || role == User.ROLE_ADMIN) {
            throw new XException(Code.UNAUTHORIZED,"无权限");
        }
        else if (teacherService.listSelect(name) == null) {
            throw new XException(Code.FORBIDDEN,"操作失败");
        }
        else if (teacherService.listSelect(name).size() == 0) {
            return ResultVo.builder()
                    .code(666)
                    .message("该导师目前没有同学选择")
                    .build();
        }
        return ResultVo.success(666,Map.of("select",teacherService.listSelect(name)));
    }
    //查找已配对的老师和同学
    @GetMapping("/students3")
    public ResultVo listSelect(@RequestAttribute("role") int role) {
        List<User> lists = teacherService.listStudentAndTeacher();
        if (role == User.ROLE_STUDENT || role == User.ROLE_ADMIN) {
            throw new XException(400,"无权限");
        }
        else if (lists == null) {
            throw new XException(400,"操作失败");
        }
        else if (lists.size() == 0) {
            return ResultVo.builder()
                    .code(666)
                    .message("目前没用配对的老师和同学")
                    .build();
        }
        return ResultVo.success(666,Map.of("TeacherAndStudent",lists));
    }
    //通过提供姓名和旧密码，更新密码
    @PutMapping("/updateP")
    public ResultVo updatePassword(@RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("newPassword") String newPassword) {
        boolean flag = teacherService.updatePassword(name,password,newPassword);
        if (!flag) {
            return ResultVo.error(Code.BAD_REQUEST,"更新失败，请重试");
        }
        return ResultVo.builder()
                .code(666)
                .message("更新成功，请妥善保存密码")
                .build();
    }
}
