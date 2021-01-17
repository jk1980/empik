package com.empik.jagee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JageeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JageeApplication.class, args);
	}

}
