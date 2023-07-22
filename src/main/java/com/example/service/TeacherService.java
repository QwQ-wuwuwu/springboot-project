package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //查找没有选导师的同学列表
    public List<User> listUnselect() {
        return userRepository.listUnSelect();
    }
    //查找已选某个老师的所有学生
    public List<User> listSelect(String name) {
        return userRepository.listSelect(name);
    }
    //查找已配对的老师和同学
    public List<User> listStudentAndTeacher() {
        return userRepository.listStudentAndTeacher();
    }
    //通过提供姓名和旧密码，更新密码
    @Transactional
    public boolean updatePassword(String name,String password,String newPassword) {
        User user = userRepository.findByName(name);
        if (passwordEncoder.matches(password, user.getPassword())){
            String temp = passwordEncoder.encode(newPassword);
            return userRepository.updatePassword(name,temp);
        }
        return false;
    }
}
