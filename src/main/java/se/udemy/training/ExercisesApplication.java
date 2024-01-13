package se.udemy.training;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The auto generated Spring Boot Exercises Application.
 * 
 * @author KFB
 */
@SpringBootApplication
public class ExercisesApplication {
	private static final Logger log = LoggerFactory.getLogger(ExercisesApplication.class);

	public static void main(String[] args) {
		log.info("Application Exercise1Application starting ...");
		SpringApplication.run(ExercisesApplication.class, args);
		log.info("Application Exercise1Application started");
	}

}
