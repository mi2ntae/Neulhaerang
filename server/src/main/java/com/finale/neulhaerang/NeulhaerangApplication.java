package com.finale.neulhaerang;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class NeulhaerangApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeulhaerangApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
			.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
			.setFieldMatchingEnabled(true);
		return modelMapper;
	}

}
