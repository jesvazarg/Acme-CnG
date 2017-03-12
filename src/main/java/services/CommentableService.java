
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentableRepository;
import domain.Commentable;

@Service
@Transactional
public class CommentableService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CommentableRepository	commentableRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public CommentableService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Commentable findOne(final int commentableId) {
		Commentable result;

		result = this.commentableRepository.findOne(commentableId);

		return result;
	}

	public Collection<Commentable> findAll() {
		Collection<Commentable> results;

		results = this.commentableRepository.findAll();

		return results;
	}

	// Other business methods -------------------------------------------------

}
