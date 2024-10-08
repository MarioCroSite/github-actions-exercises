package se.udemy.training.model;

/**
 * A Note properties class with information about all Notes.
 * 
 * @author KFB
 */
public class NoteProperties {

	/**
	 * The size property that specifies the size of a note.
	 */
	private Float size = null;

	/**
	 * The default constructor.
	 */
	public NoteProperties() {
		super();
	}

	/**
	 * The constructor using fields.
	 * 
	 * @param size the Note size
	 */
	public NoteProperties(Float size) {
		super();
		this.size = size;
	}

	public Float getSize() {
		return size;
	}

	public void setSize(Float size) {
		this.size = size;
	}
}