
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

	public Commentable findOne(int commentableId) {
		Commentable result;

		result = commentableRepository.findOne(commentableId);

		return result;
	}

	public Collection<Commentable> findAll() {
		Collection<Commentable> results;

		results = this.commentableRepository.findAll();

		return results;
	}

	// Other business methods -------------------------------------------------

	
	public Commentable findById(int commentableId){
		Commentable result;
		
		result = commentableRepository.findById(commentableId);
		
		return result;
	}
}
