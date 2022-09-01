package com.testing;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.testing.service.Unzip;

@SpringBootApplication
public class AutomationTestApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AutomationTestApplication.class, args);
		new Unzip().unzip();
	}

//	@PostConstruct
//	public static void run() throws Exception {
//		new Unzip().unzip();
//	}
}
