package se.udemy.training.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import se.udemy.training.model.NotePlain;

/**
 * The Exercise2 controller class used for all REST methods in exercise 2.
 * 
 * @author KFB
 */
@RestController
public class Exercise2Controller {

	/**
	 * The Simple Logging Facade for Java. Spring uses Logback framework.
	 */
	private static final Logger log = LoggerFactory.getLogger(Exercise2Controller.class);

	/**
	 * POST method that log the information from the received
	 * <code>NotePlain</code>.
	 * 
	 * @param note the plain note <code>NotePlain</code> (received by Spring as a
	 *             JSON Note object in the HTTP body)
	 */
	@PostMapping("/exercise2/note")
	public void logPOSTNote(@RequestBody NotePlain note) {
		log.info("Exercise 2, POST mapping received");
		log.info("Exercise 2, note information: " + note.getNoteInformation());
	}

	/**
	 * PUT method that log the information from the received <code>NotePlain</code>.
	 * 
	 * @param note the plain note <code>NotePlain</code> (received by Spring as a
	 *             JSON Note object in the HTTP body)
	 */
	@PutMapping("/exercise2/note")
	public void logPUTNote(@RequestBody NotePlain note) {
		log.info("Exercise 2, PUT mapping received");
		log.info("Exercise 2, note information: " + note.getNoteInformation());
	}

	/**
	 * DELETE method that log the note identifier path variable <code>id</code>
	 * specified in the URL.
	 * 
	 * @param id the note id specified in the URL
	 */
	@DeleteMapping("/exercise2/note/{id}")
	public void logDELETENote(@PathVariable Long id) {
		log.info("Exercise 2, DELETE mapping received, note id: " + id);
	}
}