package com.derek.reactivespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveSpringEngineApplication {

	public static void main(String[] args) {
		// Hooks.onOperatorDebug();
		SpringApplication.run(ReactiveSpringEngineApplication.class, args);
	}

}
