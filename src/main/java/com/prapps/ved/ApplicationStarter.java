package com.prapps.ved;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.prapps.ved.persistence"})
public class ApplicationStarter {
	public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
