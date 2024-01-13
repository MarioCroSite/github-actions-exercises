package se.udemy.training.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The persistent NoteDocument class that stores information about a note
 * identifier and a document identifier. A note can reference many documents and
 * a document can reference many notes, so it is a many to many relationship.
 * 
 * @author KFB
 */
@Entity
@Table(name = "NOTE_DOCUMENT")
public class NoteDocument {

	/**
	 * The primary key identifier with a sequence defined in the database.
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "PK_ID")
	private Long id = null;

	/**
	 * The <code>Note</code> identifier.
	 */
	@Column(name = "NOTE_ID")
	private Long noteId = null;

	/**
	 * The <code>Document</code> identifier.
	 */
	@Column(name = "DOC_ID")
	private String docId = "";

	/**
	 * The default constructor.
	 */
	public NoteDocument() {
		super();
	}

	/**
	 * The constructor using all fields.
	 * 
	 * @param id     the database generated identifier.
	 * @param noteId the Note identifier, must be unique
	 * @param docId  the Document identifier
	 */
	public NoteDocument(Long id, Long noteId, String docId) {
		super();
		this.id = id;
		this.noteId = noteId;
		this.docId = docId;
	}

	/**
	 * The constructor using the noteId and docId fields.
	 * 
	 * @param noteId the Note identifier, must be unique
	 * @param docId  the Document identifier
	 */
	public NoteDocument(Long noteId, String docId) {
		super();
		this.noteId = noteId;
		this.docId = docId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
}