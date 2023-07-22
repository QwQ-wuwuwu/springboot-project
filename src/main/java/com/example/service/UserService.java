package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
    //查找所有老师
    public List<User> listTeacher() {
        return userRepository.listTeacher();
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
    @Transactional
    public boolean selectTeacher(){
        return true;
    }
}
