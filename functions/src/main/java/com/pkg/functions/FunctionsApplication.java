package com.pkg.functions;

import com.pkg.functions.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FunctionsApplication implements CommandLineRunner {

	@Autowired
	private FunctionService functionService;

	public static void main(String[] args) {
		SpringApplication.run(FunctionsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		functionService.buildPipeline();
	}
}
