package com.todomanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point.
 * 
 * @author Ayhan Dardagan
 *
 */
@SpringBootApplication
public class TodomanagerApplication {

	/**
	 * Main entry.
	 * 
	 * @param args
	 *            Parameters
	 */
	public static void main(final String[] args) {
		SpringApplication.run(TodomanagerApplication.class, args);
	}
}
