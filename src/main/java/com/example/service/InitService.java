package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InitService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @EventListener(classes = ApplicationReadyEvent.class)//监听事务，当springboot容器刚准备完毕时
    @Transactional
    public void onApplicationReady() {
        long count = userRepository.count();
        String name = "赵光晶";
        String password = "123456";
        if (count == 0) {
            User user = User.builder()
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .role(User.ROLE_ADMIN)
                    .number(name)
                    .description("超级管理员")
                    .insertTime(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
    }
}
