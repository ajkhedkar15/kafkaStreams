package com.pkg.consumerone;

import com.pkg.consumerone.service.ConsumerOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerOneApplication implements CommandLineRunner {

	@Autowired
	private ConsumerOneService service;

	public static void main(String[] args) {
		SpringApplication.run(ConsumerOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.buildPipeline();
	}
}
