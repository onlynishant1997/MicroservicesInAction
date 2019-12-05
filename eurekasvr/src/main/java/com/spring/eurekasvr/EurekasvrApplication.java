package com.spring.eurekasvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekasvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekasvrApplication.class, args);
	}

}
