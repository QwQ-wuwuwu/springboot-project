package com.example.controller;

import com.example.entity.StartTime;
import com.example.entity.User;
import com.example.service.AdminService;
import com.example.vo.Code;
import com.example.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PutMapping("/time")
    public ResultVo SetTime(@RequestBody StartTime time) {
        adminService.setTime(time.getStartTime(),time.getEndTime());
        return ResultVo.builder()
                .code(666)
                .message("更新成功")
                .build();
    }
    //添加学生名单
    @PostMapping("/addS")
    public ResultVo addListStudent(@RequestBody List<User> students) {
        if (!adminService.addListStudent(students)) {
            return ResultVo.builder()
                    .data(null)
                    .code(Code.FORBIDDEN)
                    .message("添加失败").build();
        }
        return ResultVo.success(null,"添加成功");
    }
    //添加老师名单
    @PostMapping("/addT")
    public ResultVo addListTeacher(@RequestBody List<User> teachers) {
        if (!adminService.addListTeacher(teachers)) {
            return ResultVo.builder()
                    .data(null)
                    .code(Code.FORBIDDEN)
                    .message("添加失败").build();
        }
        return ResultVo.success(null,"添加成功");
    }
    //重置密码
    @PutMapping("/{newPassword}")
    public ResultVo reSetPassword(@PathVariable String newPassword) {
        if (!adminService.reSetPassword(newPassword)) {
            return ResultVo.error(Code.UNAUTHORIZED,"重置失败");
        }
        return ResultVo.success(null,"重制成功");
    }
}
