package se.udemy.training.parser;

/**
 * A Note parser exception that can be used for all Note parser exceptions.
 * 
 * @author KFB
 *
 */
public class NoteParserException extends Exception {
	
	public NoteParserException(String errorMessage) {
		super(errorMessage);
	}
}
