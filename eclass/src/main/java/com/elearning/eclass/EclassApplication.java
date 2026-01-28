package com.elearning.eclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
	"com.elearning.elearning_sdk"
})
public class EclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(EclassApplication.class, args);
	}

}
