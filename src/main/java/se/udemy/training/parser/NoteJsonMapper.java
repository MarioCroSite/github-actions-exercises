package se.udemy.training.parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;

import se.udemy.training.model.Note;

public class NoteJsonMapper extends ArrayJsonParser {

	private Note note = null;
	
	/**
	 * Set the Note field value with the value from the current token.
	 */
	@Override
	protected void setObjectValue(String fieldName, JsonParser parser) throws IOException, NoteParserException {
		
		if (fieldName.equals("id")) {
			note.setId(Long.valueOf(getFieldValue(parser)));
		} else if (fieldName.equals("noteId")) {
			note.setNoteId(Long.valueOf(getFieldValue(parser)));
		} else if (fieldName.equals("header")) {
			note.setHeader(getFieldValue(parser));
		} else if (fieldName.equals("body")) {
			note.setBody(getFieldValue(parser));
		} else if (fieldName.equals("comment")) {
			note.setComment(getFieldValue(parser));
		} else {
			throw new NoteParserException("The field is not known by the Note: " + fieldName);
		}
	}	
	
	@Override
	protected void createObject() {
		note = new Note();
		
	}

	@Override
	protected Note getObject() {
		return note;
	}
	
	/**
	 * Return the list of Notes.
	 * 
	 * @param objects the list of JsonAbstract objects, that must be Notes.
	 * @return
	 */
	public List<Note> getNotes(ArrayList<JsonAbstract> objects) throws NoteParserException {
		Iterator<JsonAbstract> it = null;
		JsonAbstract jsonObject = null;
		List<Note> notes = new ArrayList<Note>();
		
		
		it = objects.iterator();
		while (it.hasNext()) {
			jsonObject = it.next();
			if (jsonObject instanceof Note) {
				notes.add((Note)jsonObject);
			} else {
				throw new NoteParserException("Can not add object to a Note array as the object is not a Note: " + jsonObject.toString());
			}
		}
		
		return notes;
	}
}
