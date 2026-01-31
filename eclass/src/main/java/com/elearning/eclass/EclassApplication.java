package com.elearning.eclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.elearning.elearning_sdk",
	"com.pagination.mongodb",
	"com.elearning.eclass",
})
public class EclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(EclassApplication.class, args);
	}

}
