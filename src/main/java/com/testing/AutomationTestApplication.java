package com.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.testing.service.Unzip;


@SpringBootApplication
public class AutomationTestApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AutomationTestApplication.class, args);
		new Unzip().unzip();
	}
}
