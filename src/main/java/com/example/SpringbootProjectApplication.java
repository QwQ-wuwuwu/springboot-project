package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy//扫描自定义切面（aop）
public class SpringbootProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootProjectApplication.class, args);
	}
}
