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
@RequestMapping("/api/teacher")//已经在拦截器实现了权限验证，所以这里的操作就不要再次进行权限验证
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    //查找没有选导师的同学列表
    @GetMapping("/students1")
    public ResultVo listUnSelect() {
        if (teacherService.listUnselect().size() == 0) {
            return ResultVo.builder()
                    .code(666)
                    .message("全部学生都已选择导师")
                    .build();
        }
        if (teacherService.listUnselect() == null) {
            return ResultVo.builder()
                    .code(400)
                    .message("操作失败")
                    .build();
        }
        return ResultVo.success(666, Map.of("UnSelect",teacherService.listUnselect()));
    }
    //查找已选某个老师的所有学生
    @GetMapping("/students2/{tid}")//通常来说，用户使用该功能更多的是点击该老师，而不是去输入老师的名字
    public ResultVo listSelect(@PathVariable long tid) {
        if (teacherService.listSelect(tid) == null) {
            return ResultVo.builder()
                    .code(400)
                    .message("操作失败")
                    .build();
        }
        if (teacherService.listSelect(tid).size() == 0) {
            return ResultVo.builder()
                    .code(666)
                    .message("该导师目前没有同学选择")
                    .build();
        }
        return ResultVo.success(666,Map.of("select",teacherService.listSelect(tid)));
    }
    //查找已配对的老师和同学
    @GetMapping("/students3")
    public ResultVo listSelect() {
        List<User> lists = teacherService.listStudentAndTeacher();
        if (lists == null) {
            return ResultVo.builder()
                    .code(400)
                    .message("操作失败")
                    .build();
        }
        if (lists.size() == 0) {
            return ResultVo.builder()
                    .code(666)
                    .message("目前没用配对的老师和同学")
                    .build();
        }
        return ResultVo.success(666,Map.of("TeacherAndStudent",lists));
    }
    //通过提供姓名和旧密码，更新密码
    @PutMapping("/password")
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
