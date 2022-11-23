package com.derek.reactivespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class ReactiveSpringEngineApplication {

	public static void main(String[] args) {
		// Hooks.onOperatorDebug();

		BlockHound.builder()
						.allowBlockingCallsInside(TemplateEngine.class.getCanonicalName(), "process")
						.install();

		SpringApplication.run(ReactiveSpringEngineApplication.class, args);
	}

}
