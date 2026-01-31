package com.elearning.elearning_sdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	"com.pagination.mongodb"
})
public class ElearningSdkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElearningSdkApplication.class, args);
	}

}
