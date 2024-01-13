package se.udemy.training.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import se.udemy.training.ExercisesApplication;

/**
 * The Exercise1 controller class used for all REST methods in exercise 1.
 * 
 * @author KFB
 */
@RestController
public class Exercise1Controller {

	/**
	 * The Simple Logging Facade for Java. Spring uses Logback framework.
	 */
	private static final Logger log = LoggerFactory.getLogger(ExercisesApplication.class);

	/**
	 * The Spring core environment.
	 */
	@Autowired
	protected Environment environment = null;

	/**
	 * GET method that log a message.
	 */
	@GetMapping("/exercise1")
	public void writeToConsole() {
		log.info("Exercise 1, get mapping received ");
	}

	/**
	 * GET method that log the user number and uses the Java object to calculate a
	 * new number.
	 * 
	 * @param usernumber the path variable user number
	 */
	@GetMapping("/exercise1/{usernumber}")
	public void writeNumberToConsole(@PathVariable Long usernumber) {
		log.info("Exercise 1, get mapping received, user number: " + usernumber);
		usernumber += 333;
		log.debug("Exercise 1, user number after adding: " + usernumber);
	}

	/**
	 * GET method that read a value from the configuration file and then write a log
	 * message.
	 */
	@GetMapping("/exercise1/config")
	public void writeConfigValueToConsole() {
		String configValue = "";

		configValue = environment.getProperty("se.udemy.exercise1");
		log.info("Exercise 1, config value: " + configValue);
	}
}