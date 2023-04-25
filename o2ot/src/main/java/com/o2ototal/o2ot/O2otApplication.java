package com.o2ototal.o2ot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
//@EntityScan
public class O2otApplication {

	public static void main(String[] args) {
		SpringApplication.run(O2otApplication.class, args);
	}

}
