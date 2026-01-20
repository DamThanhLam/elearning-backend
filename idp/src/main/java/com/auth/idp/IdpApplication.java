package com.auth.idp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	"com.elearning"
})
public class IdpApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdpApplication.class, args);
	}

}
