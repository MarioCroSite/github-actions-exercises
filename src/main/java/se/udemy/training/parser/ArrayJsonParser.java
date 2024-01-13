package se.udemy.training.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The ArrayJsonParser class that parses a JSON array and JSON objects. It creates
 * a list of Objects from the JSON objects.
 * 
 * @author KFB
 */
public abstract class ArrayJsonParser {

	/**
	 * The Simple Logging Facade for Java. Spring uses Logback framework.
	 */
	private static final Logger log = LoggerFactory.getLogger(ArrayJsonParser.class);

	/**
	 * The list of objects.
	 */
	private ArrayList<JsonAbstract> objects = new ArrayList<JsonAbstract>();
	
	/**
	 * Set the object field value with the value of the current token.
	 *  
	 * @param fieldName the current token field name
	 * @param parser	the JSON Parser at the current token
	 * 
	 * @throws IOException
	 * @throws NoteParserException
	 */
	protected abstract void setObjectValue(String fieldName, JsonParser parser) throws IOException, NoteParserException;	
	
	/**
	 * Returns an object that has been created when all fields has been read from the JSON object.
	 * 
	 * @return the object that has been created.
	 */
	protected abstract JsonAbstract getObject();
	
	/**
	 * Create a new object instance to set the values to.
	 */
	protected abstract void createObject();
	
	/**
	 * Returns the text value if the token is a VALUE token.
	 * 
	 * @param parser the JsonParser with the token position at a field
	 * 
	 * @return the text value from the Value token
	 * 
	 * @throws IOException         if there is an exception in the Jackson parser
	 * @throws NoteParserException if the token does not start with 'VALUE'
	 */
	protected String getFieldValue(JsonParser parser) throws IOException, NoteParserException {
		String tokenAsString = "";

		tokenAsString = parser.nextToken().name();
		if (tokenAsString.startsWith("VALUE")) {
			return parser.getText();
		} else {
			throw new NoteParserException("Expected VALUE token to start with 'VALUE'");
		}
	}

	/**
	 * Parses the JSON object and add an object to the list.
	 * 
	 * @param parser the JsonParser with the token position at the beginning of the
	 *               JSON object
	 * 
	 * @throws IOException         if there is an exception in the Jackson parser
	 * @throws NoteParserException if there is an error getting the field value
	 */
	private void parseObject(JsonParser parser) throws IOException, NoteParserException {
		String tokenName = "";
		JsonToken token = null;
		String text = "";

		try {
			log.info("Parsing JSON object");
			createObject();
			while (parser.nextToken() != null) {
				token = parser.getCurrentToken();
				log.debug(parser.getText());
				tokenName = token.name();
				log.debug("Token as String: " + tokenName);
				if (tokenName.equals("FIELD_NAME")) {
					text = parser.getText();
					setObjectValue(text, parser);
				} else if (tokenName.equals("END_OBJECT")) {
					break;
				}
			}
			
			objects.add(getObject());
		} catch (Exception e) {
			log.error("Error parsing the JSON Object", e);
			throw e;
		}
	}

	/**
	 * Parses an Object array.
	 * 
	 * @param parser the JsonParser with the token position at the beginning of the
	 *               Note array
	 * 
	 * @throws IOException         if there is an exception in the Jackson parser
	 * @throws NoteParserException if there is an error getting the field value
	 */
	private void parseObjectArray(JsonParser parser) throws IOException, NoteParserException {
		JsonToken token = null;

		try {
			log.info("Parsing JSON Object array");
			while (parser.nextToken() != null) {
				token = parser.getCurrentToken();
				log.debug(parser.getText());

				if (token.equals(JsonToken.START_OBJECT)) {
					parseObject(parser);
				} else if (token.equals(JsonToken.END_ARRAY)) {
					break;
				}
			}
			log.info("Finished parsing JSON object array");
		} catch (Exception e) {
			log.error("Error parsing the JSON object array", e);
			throw e;
		}
	}

	/**
	 * Parse a JSON Array or Object and return a list of objects.
	 * 
	 * @param payload the JsonNode that begins the JSON tree.
	 * 
	 * @return a list of Objects
	 * 
	 * @throws IOException         if there is an error in the Jackson parser
	 * @throws NoteParserException if there is any unexpected values in the JSON
	 *                             array
	 */
	public ArrayList<JsonAbstract> parseJson(JsonNode payload) throws IOException, NoteParserException {
		JsonParser parser = null;
		JsonToken token = null;

		try {
			parser = JsonFactory.builder().build().createParser(payload.toPrettyString().getBytes());
			token = parser.getCurrentToken();
			while (parser.nextToken() != null) {
				token = parser.getCurrentToken();
				log.info(parser.getText());
				if (token.equals(JsonToken.START_ARRAY)) {
					parseObjectArray(parser);
					break;
				} else if (token.equals(JsonToken.START_OBJECT)) {
					parseObject(parser);
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error parsing JSON structure", e);
			throw e;
		}

		return objects;
	}
	
}