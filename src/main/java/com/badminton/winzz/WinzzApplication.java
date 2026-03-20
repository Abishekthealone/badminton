package com.badminton.winzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WinzzApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinzzApplication.class, args);
	}

}
