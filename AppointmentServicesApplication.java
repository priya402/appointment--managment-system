package com.AppointmentManagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
  // Allow requests from the frontend on port 3000
public class AppointmentServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentServicesApplication.class, args);
	}

}
