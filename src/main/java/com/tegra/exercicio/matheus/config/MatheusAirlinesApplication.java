package com.tegra.exercicio.matheus.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MatheusAirlinesApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		new MatheusAirlinesApplication()
			.configure(new SpringApplicationBuilder(MatheusAirlinesApplication.class))
			.run(args);
	}

}
