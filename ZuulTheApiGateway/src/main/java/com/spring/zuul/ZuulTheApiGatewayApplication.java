package com.spring.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@RefreshScope
@EnableZuulProxy
@SpringBootApplication
public class ZuulTheApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulTheApiGatewayApplication.class, args);
	}

}
