package com.jeekhan.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.jeekhan.wx.mapper")
@EnableScheduling
public class WXMPApplication {

	public static void main(String[] args) {
		SpringApplication.run(WXMPApplication.class, args);
	}
}
