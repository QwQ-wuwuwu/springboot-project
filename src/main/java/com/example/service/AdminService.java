package com.example.service;

import com.example.entity.StartTime;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StartTime startTime;
    //设置开始时间
    public void setTime(LocalDateTime start, LocalDateTime end) {
        startTime.setStartTime(start);
        startTime.setEndTime(end);
    }
    //添加学生名单(名单只包含姓名)
    @Transactional
    public boolean addListStudent(List<User> students) {
        if (students.size() == 0) return false;
        for (User student : students) {
            student.setPassword(passwordEncoder.encode(student.getName()));
            student.setNumber(student.getName());
            student.setRole(User.ROLE_STUDENT);
            student.setInsertTime(LocalDateTime.now());
            userRepository.save(student);
        }
        return true;
    }
    //添加老师名单(名单只包含姓名)
    @Transactional
    public boolean addListTeacher(List<User> teachers) {
        if (teachers.size() == 0) return false;
        for (User teacher : teachers) {
            teacher.setPassword(passwordEncoder.encode(teacher.getName()));
            teacher.setNumber(teacher.getName());
            teacher.setRole(User.ROLE_TEACHER);
            teacher.setTotal(10);
            teacher.setCount(0);
            teacher.setInsertTime(LocalDateTime.now());
            userRepository.save(teacher);
        }
        return true;
    }
    //重置所有密码
    @Transactional
    public boolean reSetPassword(String newPassword) {
        String temp = passwordEncoder.encode(newPassword);
        return userRepository.reSetPassword(temp);
    }
}
