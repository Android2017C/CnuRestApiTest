package com.cnu;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan()
public class CnuRestApiTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnuRestApiTestApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {

		return new ModelMapper();
	}
}
