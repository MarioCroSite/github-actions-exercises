package se.udemy.training.controller;

import javax.management.BadStringOperationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;
import se.udemy.training.model.NoteProperties;

/**
 * The Exercise3 controller class used for all REST methods in exercise 3.
 * 
 * @author KFB
 */
@RestController
public class Exercise3Controller {

	/**
	 * The Simple Logging Facade for Java. Spring uses Logback framework.
	 */
	private static final Logger log = LoggerFactory.getLogger(Exercise3Controller.class);

	/**
	 * The properties that applies to all Notes.
	 */
	private NoteProperties noteProperties = null;

	/**
	 * The Spring core environment.
	 */
	@Autowired
	protected Environment environment = null;

	/**
	 * Spring's general procedure to send response exceptions to calling
	 * applications. Reads the message and status from the application level
	 * exception.
	 * 
	 * @param rse the ResponseStatusException that contains status code, message and
	 *            exception.
	 * @return a ResponseEntity with HTTP status code, body and header.
	 */
	@ExceptionHandler
	public ResponseEntity<String> handle(ResponseStatusException rse) {
		return new ResponseEntity<String>(rse.getMessage(), rse.getStatusCode());
	}

	/**
	 * Get the note size from application.properties and initializes the
	 * <code>NoteProperties</code> class.
	 */
	@PostConstruct
	protected void init() {
		Float noteSize = null;

		try {
			noteSize = Float.valueOf(environment.getProperty("se.udemy.exercise3.note.size"));
			log.info("Exercise 3 PostConstruct, note size from application.properties: " + noteSize);
			this.noteProperties = new NoteProperties(noteSize);
			log.info("Exercise 3 PostConstruct, note size from property object: " + this.noteProperties.getSize());
		} catch (Exception e) {
			log.error("Error initializing the Note properties: " + e.getMessage());
		}
	}

	/**
	 * GET method that returns an error message to the calling client.
	 */
	@GetMapping("/exercise3/note")
	public void returnErrorMessage() {
		try {
			log.info("Exercise 3, return an error message");
			throw new BadStringOperationException("Used to simulate an error in the method");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Exercise 3, return an error message");
		}
	}

	/**
	 * GET method that returns the note properties.
	 * 
	 * @return the note properties
	 */
	@GetMapping("/exercise3/note/properties")
	public NoteProperties getNoteProperties() {
		log.info("Exercise 3, return note properties");
		return noteProperties;
	}
}