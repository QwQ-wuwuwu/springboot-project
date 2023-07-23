package com.example.test;

import com.example.config.SnowflakeGenerator;
import com.example.entity.StartTime;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.AdminService;
import com.example.service.TeacherService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
public class BCryptTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void test_01() {
        String pwd = "123456";
        String ans = passwordEncoder.encode(pwd);
        //System.out.println(ans);
        //System.out.println(passwordEncoder.matches("159357",ans));
        //$2a$10$8W4c4swsC1/HPKBh6HeziuVNl4pZ29NgE9dvwDLy6AIXFNHAtO7t2
        log.debug("{}",ans);
    }
    @Autowired
    private UserService userService;
    @Test
    public void test_02() {
        User user = userService.findByName("赵光晶");
        System.out.println(user.getPassword());
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SnowflakeGenerator generator;
    @Test
    public void test_03() {
        User user = User.builder()
                .name("学生五")
                .password(passwordEncoder.encode("学生五"))
                .role(0)
                .number("学生五")
                .teacherName("BO")
                .teacherId((long) generator.hashCode())
                .selectTime(LocalDateTime.now())
                .insertTime(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }
    @Test
    public void test_06() {
        User user = User.builder()
                .name("student1")
                .password(passwordEncoder.encode("student1"))
                .role(User.ROLE_STUDENT)
                .number("student1")
                .insertTime(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }
    @Test
    public void test_04() {
        System.out.println(userService.updatePassword("学生一","159357","532129"));
    }
    @Autowired
    private TeacherService teacherService;
    @Test
    public void test_05() {
        List<User> list = teacherService.listSelect(123156516621564L);
        for (User user : list) {
            System.out.println(user);
        }
    }
    @Autowired
    private AdminService adminService;
    @Autowired
    private StartTime startTime;
    @Test
    public void test_07() {
        LocalDateTime start = LocalDateTime.of(2023,8,15,12,00 );
        LocalDateTime end = LocalDateTime.of(2023,9,15,12,00);
        adminService.setTime(start,end);
    }
    @Test
    public void test_08() {
        System.out.println(startTime.getStartTime());
        System.out.println(startTime.getEndTime());
    }
    @Test
    public void test_09() {
        /*List<User> list = userService.listTeacher(1132275642500456448L,0);
        for (User user : list) {
            System.out.println(user);
        }*/
    }
}
