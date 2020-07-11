package com.joseyustiz.walmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.joseyustiz.walmart")
public class WalmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalmartApplication.class, args);
	}

}
