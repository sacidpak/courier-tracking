package com.sacidpak.courier.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.sacidpak.courier.tracking.domain.entity")
@EnableJpaRepositories(basePackages = "com.sacidpak.courier.tracking.domain.repository")
public class CourierTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierTrackingApplication.class, args);
	}

}
