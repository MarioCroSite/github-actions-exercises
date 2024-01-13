package se.udemy.training.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import se.udemy.training.model.Note;
import se.udemy.training.model.document.Document;
import se.udemy.training.parser.DocumentJsonMapper;
import se.udemy.training.parser.JsonAbstract;
import se.udemy.training.parser.NoteJsonMapper;
import se.udemy.training.parser.NoteJsonParser;

/**
 * The Exercise5 controller class used for all REST methods in exercise 5.
 * 
 * @author KFB
 */
@RestController
public class Exercise5Controller {

	/**
	 * The Simple Logging Facade for Java. Spring uses Logback framework.
	 */
	private static final Logger log = LoggerFactory.getLogger(Exercise5Controller.class);
	
	/**
	 * The exception handler from previous exercise.
	 * 
	 * Spring's general procedure to send response exceptions to calling
	 * applications. Read the message and status from the application level
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
	 * Convert all notes to a CSV format, print them to standard out and then return
	 * them in a String format.
	 * 
	 * @param notes the list of notes to convert to CSV format
	 * 
	 * @return a String with all the notes in a CSV format
	 */
	private String printCSVNote(ArrayList<Note> notes) {
		Note note = null;
		Iterator<Note> iterator = null;
		String separator = ";";
		String result = "";

		try {
			iterator = notes.iterator();
			while (iterator.hasNext()) {
				note = iterator.next();
				result += "" + note.getNoteId() + separator + note.getHeader() + separator + note.getBody() + separator
						+ note.getComment() + System.lineSeparator();
			}
		} catch (Exception e) {
			log.error("Error creating Note CSV format", e);
			throw e;
		}

		System.out.print(result);
		return result;
	}

	/**
	 * Convert all documents to a CSV format, print them to standard out and then return
	 * them in a String format.
	 * 
	 * @param notes the list of documents to convert to CSV format
	 * 
	 * @return a String with all the documents in a CSV format
	 */
	private String printCSVDocument(Document[] documents) {
		Document document = null;
		String separator = ";";
		String result = "";

		try {
			for (int i = 0; i < documents.length; i++) {
				document = documents[i];
				result += "" + document.getId() + separator + document.getDocId() + separator + document.getName() + separator
						+ document.getDescription()+ separator + document.getPath() + System.lineSeparator();
			} 
		} catch (Exception e) {
			log.error("Error creating Document CSV format", e);
			throw e;
		}

		System.out.print(result);
		return result;
	}

	/**
	 * POST method that log the raw String received.
	 * 
	 * @param payload the String that contains all information from the HTTP body
	 */
	@PostMapping("/exercise5/note/string")
	public void writeToConsole(@RequestBody String payload) {
		log.info("Exercise 5, get mapping received, raw payload: " + payload);
	}

	/**
	 * POST method that parses the JSON array or object using Spring's Fasterxml
	 * Jackson parser.
	 * 
	 * @param payload the JsonNode (received by Spring as a JSON Note Array or
	 *                object in the HTTP body)
	 */
	@PostMapping("/exercise5/note/jsonnode")
	public String writeToConsole(@RequestBody JsonNode payload) {
		ArrayList<Note> notes = null;

		try {
			log.info("Exercise 5, GET mapping received, JsonNode payload: " + payload);
			log.info("Exercise 5, start parsing JsonNode payload");
			notes = new NoteJsonParser().parserJson(payload);
			log.info("Exercise 5, finished parsing JsonNode payload");
			return printCSVNote(notes);
		} catch (Exception e) {
			log.error("Error parsing the Note array or creating the CSV format", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Error parsing. Message: " + e.getMessage());
		}
	}

	/**
	 * POST method that serializes the Note object to a String and log the value.
	 * 
	 * @param note the Note to serialize (received by Spring as a JSON Note object
	 *             in the HTTP body)
	 */
	@PostMapping("exercise5/serialize")
	public void writeNoteSerializedToConsole(@RequestBody Note note) {
		ObjectMapper mapper = new ObjectMapper();
		String noteString = "";

		try {
			log.info("Exercise 5, GET mapping received, Note object info: " + note.noteInformation());
			noteString = mapper.writeValueAsString(note);
			log.info("Exercise 5, serialized value: " + noteString);
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			noteString = mapper.writeValueAsString(note);
			log.info("Exercise 5, indented serialized value: " + noteString);
		} catch (Exception e) {
			log.error("Exercise 5, an error occurred serializing the Note using ObjectMapper", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Error serializing the Note. Message: " + e.getMessage());
		}
	}

	/**
	 * POST method that deserialize the String JSON object to a Java Note object and
	 * then log the note information.
	 * 
	 * @param payload the JSON object in a String value
	 */
	@PostMapping("exercise5/note/deserialize")
	public void deserializeStringNoteToConsole(@RequestBody String payload) {
		ObjectMapper mapper = new ObjectMapper();
		Note deserializedNote = null;

		log.info("Exercise 5, GET mapping received, serialized payload: " + payload);
		try {
			deserializedNote = mapper.readValue(payload, Note.class);
			log.info("Deserialized Note information: " + deserializedNote.noteInformation());
		} catch (Exception e) {
			log.error("Exercise 5, an error occurred deserilizing the Note using ObjectMapper", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Error deserializing the Note. Message: " + e.getMessage());
		}
	}
	
	/**
	 * Parses a JSON array of Notes and returns an object array of Notes. Uses the new 
	 * <code>NoteJsonMapper</code> instead of the deprecated <code>NoteJsonParser</code>.
	 * 
	 * @param payload	the JSON array of Notes
	 * @return	an object list of Notes
	 */
	@PostMapping(path = "exercise5/note/notemapper", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Note> getNotes(@RequestBody JsonNode payload) {
		ArrayList<JsonAbstract> objects = null;
		NoteJsonMapper noteMapper = null;
		
		try {
			log.info("Exercise 5, create note array with NoteJsonMapper: " + payload);
			noteMapper = new NoteJsonMapper();
			objects = noteMapper.parseJson(payload);
			log.info("Exercise 5, done creating note array with NoteJsonMapper");
			return noteMapper.getNotes(objects);
		} catch (Exception e) {
			log.error("Exercise 5, an error occurred creating a list of notes", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Error creating a list of notes: " + e.getMessage());
		}
	}
	
	/**
	 * Parses a JSON array of Documents and returns an object array of Documents. Uses the new 
	 * <code>DocumentJsonMapper</code> instead of the deprecated <code>NoteJsonParser</code>.
	 * 
	 * @param payload	the JSON array of Documents
	 * @return	a list of Documents
	 */
	@PostMapping(path = "exercise5/document/documentmapper", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Document> getDocuments(@RequestBody JsonNode payload) {
		ArrayList<JsonAbstract> objects = null;
		DocumentJsonMapper documentMapper = null;
		
		try {	
			log.info("Exercise 5, create document array with DocumentJsonMapper: " + payload);
			documentMapper = new DocumentJsonMapper();
			objects = documentMapper.parseJson(payload);
			log.info("Exercise 5, done creating note array with DocumentJsonMapper");
			return documentMapper.getDocuments(objects);
		} catch (Exception e) {
			log.error("Exercise 5, an error occurred creating a list of documents", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Error creating a list of documents: " + e.getMessage());
		}
	}
	
	/**
	 * Parses a JSON array of Documents and returns a CSV list of objects. Uses a
	 * <code>JsonParser</code> and a <code>ObjectMapper</code> to create the array
	 * of objects and then converts the array to a CSV list. 
	 * 
	 * @param payload	the JSON array of Documents
	 * @return	a CSV list of Documents
	 */
	@PostMapping(path = "exercise5/document/objectmapper", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getJacksonDocuments(@RequestBody JsonNode payload) {
		Document[] objects = new Document[1];
		ObjectMapper objMapper = null;
		JsonParser parser = null;
		
		try {	
			log.info("Exercise 5, create document array with ObjectMapper: " + payload);
			objMapper = new ObjectMapper();
			parser = JsonFactory.builder().build().createParser(payload.toPrettyString().getBytes());	
			objects = objMapper.readValue(parser, objects.getClass());
			log.info("Exercise 5, done creating document array with ObjectMapper");
			return printCSVDocument(objects);
		} catch (Exception e) {
			log.error("Exercise 5, an error occurred creating a list of documents", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Error creating a list of documents: " + e.getMessage());
		}
	}
}