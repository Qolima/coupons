package com.pineslack.coupons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CouponsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponsApplication.class, args);
	}

}
