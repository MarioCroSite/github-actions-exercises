package se.udemy.training.parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;

import se.udemy.training.model.document.Document;

public class DocumentJsonMapper extends ArrayJsonParser {

	private Document doc = null;
	
	/**
	 * Set the Note field value with the value from the current token.
	 */
	@Override
	protected void setObjectValue(String fieldName, JsonParser parser) throws IOException, NoteParserException {
		
		if (fieldName.equals("id")) {
			doc.setId(Long.valueOf(getFieldValue(parser)));
		} else if (fieldName.equals("docId")) {
			doc.setDocId(getFieldValue(parser));
		} else if (fieldName.equals("name")) {
			doc.setName(getFieldValue(parser));
		} else if (fieldName.equals("description")) {
			doc.setDescription(getFieldValue(parser));
		} else if (fieldName.equals("path")) {
			doc.setPath(getFieldValue(parser));
		} else {
			throw new NoteParserException("The field is not known by the Document: " + fieldName);
		}
	}	
	
	@Override
	protected Document getObject() {
		return doc;
	}
	
	/**
	 * Return the list of Documents.
	 * 
	 * @param objects the list of JsonAbstract objects, that must be Documents.
	 * @return
	 */
	public List<Document> getDocuments(ArrayList<JsonAbstract> objects) throws NoteParserException {
		Iterator<JsonAbstract> it = null;
		JsonAbstract jsonObject = null;
		List<Document> docs = new ArrayList<Document>();
		
		
		it = objects.iterator();
		while (it.hasNext()) {
			jsonObject = it.next();
			if (jsonObject instanceof Document) {
				docs.add((Document)jsonObject);
			} else {
				throw new NoteParserException("Can not add object to a Document array as the object is not a Document: " + jsonObject.toString());
			}
		}
		
		return docs;
	}

	@Override
	protected void createObject() {
		this.doc = new Document();
		
	}
}
