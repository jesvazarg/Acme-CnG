
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Administrator;
import domain.Comment;
import domain.Commentable;

@Service
@Transactional
public class CommentService {

	//ManagedRepository----------------------------------------------

	@Autowired
	private CommentRepository	commentRepository;

	//Supporting Services--------------------------------------------

	@Autowired
	private ActorService		actorService;


	//Constructor----------------------------------------------------

	public CommentService() {
		super();
	}

	//SimpleCRUDMethods ----------------------------------------------

	public Comment findOne(final int commentId) {
		Assert.notNull(commentId);
		final Comment result = this.commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;

	}

	public Collection<Comment> findAll() {

		return this.commentRepository.findAll();
	}

	public Comment create(final Commentable postedTo) {

		final Comment result = new Comment();

		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		result.setPostedBy(principal);

		final Calendar thisMoment = Calendar.getInstance();
		result.setPostedMoment(thisMoment.getTime());

		Assert.notNull(postedTo);
		result.setPostedTo(postedTo);

		result.setBanned(false);

		return result;
	}

	public Comment save(final Comment comment) {

		Assert.notNull(comment);
		Assert.isTrue(this.actorService.findByPrincipal().equals(comment.getPostedBy()));

		final Comment result = this.commentRepository.save(comment);

		return result;
	}

	public void delete(final Comment comment) {
		Assert.isTrue((this.actorService.findByPrincipal().equals(comment.getPostedBy()))||(this.actorService.findByPrincipal() instanceof Administrator));
		Assert.notNull(comment);

		this.commentRepository.delete(comment);
	}

	// Other business methods -------------------------------------------------

	public void banComment(final Comment comment) {

		Assert.notNull(comment);

		comment.setBanned(true);

		this.commentRepository.save(comment);

	}

	public Collection<Comment> getCommentsFilterBan(final Collection<Comment> comments) {
		final Collection<Comment> result = new ArrayList<Comment>();
		Actor actor;
		Boolean isAdmin;
		Boolean view = true;

		actor = this.actorService.findByPrincipal();
		isAdmin = this.actorService.checkAuthority(actor, "ADMIN");
		//		authorities = actor.getUserAccount().getAuthorities();
		//		isAdmin = authorities.contains("ADMIN");
		System.out.println("es admin " + isAdmin);

		for (final Comment c : comments) {
			if (c.getBanned()) {
				System.out.println("es ban " + c.getBanned());
				if (!isAdmin && (c.getPostedBy().getId() != actor.getId())) {
					System.out.println(c.getPostedBy().getId() + " es igual " + actor.getId());
					view = false;
				}
			}
			if (view)
				result.add(c);
			view = true;
		}
		return result;
	}

}
