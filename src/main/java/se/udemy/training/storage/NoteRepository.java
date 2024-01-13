package se.udemy.training.storage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.udemy.training.model.Note;

/**
 * The NoteRepository interface used to specify a JPA repository and declare
 * methods.
 * 
 * @author KFB
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	/**
	 * Find a Note using a note identifier.
	 * 
	 * @param noteId the note identifier
	 * 
	 * @return an Optional Note that can contain an empty result
	 */
	Optional<Note> findNoteByNoteId(Long noteId);

	/**
	 * Find Notes using a specified header value.
	 * 
	 * @param header the header to search for
	 * 
	 * @return a list of all Notes with the specified header
	 */
	Note[] findNotesByHeader(String header);

}
