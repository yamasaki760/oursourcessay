package com.timcast.flurbovurbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.timcast")
@EnableScheduling
public class FlurboverboApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlurboverboApplication.class, args);
	}

}
