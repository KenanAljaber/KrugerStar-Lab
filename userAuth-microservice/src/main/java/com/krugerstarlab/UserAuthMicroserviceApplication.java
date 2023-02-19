package com.krugerstarlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserAuthMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthMicroserviceApplication.class, args);
	}

}
