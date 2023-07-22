package com.example.service;

import com.example.entity.User;
import com.example.exception.XException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    //查找所有老师,已选导师的同学无法查找
    public List<User> listTeacher(long uid,int role) {
        User user = userRepository.findById(uid);
        String temp = user.getTeacherName();
        if (temp != null && role == User.ROLE_STUDENT) {
            throw new XException(500,"已选导师，不在加载列表");
        }
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
    //选择导师
    @Transactional
    public boolean selectTeacher(long uid,long tid){
        if (uid == 0 || tid == 0) {
            return false;
        }
        User s = userRepository.findByIdForUpdate(uid);
        User t = userRepository.findByIdForUpdate(tid);
        if (s == null || t == null) return false;
        if (s.getTeacherName() !=null) {
            throw new XException(500,"已选导师,不可重复选择且不可更改");
        }
        if (t.getTotal() == 0) {
            throw new XException(500,"导师数量已满");
        }

        s.setSelectTime(LocalDateTime.now());
        s.setTeacherId(t.getId());
        s.setTeacherName(t.getName());

        t.setTotal(t.getTotal() - 1);
        t.setCount(t.getCount() + 1);
        userRepository.save(s);
        userRepository.save(t);
        return true;
    }
}
