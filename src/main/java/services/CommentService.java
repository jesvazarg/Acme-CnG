package services;

import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;


@Service
@Transactional
public class CommentService {

	//ManagedRepository----------------------------------------------
	
	private CommentRepository commentRepository;
	
	//Supporting Services--------------------------------------------
	
	private ActorService actorService;
	
	//Constructor----------------------------------------------------
	
	public CommentService(){
		super();
	}
	
	//SimpleCRUDMethods ----------------------------------------------
	
	public Comment findOne(int commentId){
		Assert.notNull(commentId);
		Comment result=commentRepository.findOne(commentId);
		Assert.notNull(result);
		
		return result;
		
	}
	
	public Collection<Comment> findAll(){
		
		return commentRepository.findAll();
	}
	
	public Comment create (Commentable postedTo){
		
		Comment result = new Comment();
		
		Actor principal = actorService.findByPrincipal();
		Assert.notNull(principal);
		result.setPostedBy(principal);
		
		Calendar thisMoment = Calendar.getInstance();
		result.setPostedMoment(thisMoment.getTime());
		
		Assert.notNull(postedTo);
		result.setPostedTo(postedTo);
		
		result.setBanned(false);
		
		return result;	
	}
	
	public Comment save(Comment comment){
		
		Assert.notNull(comment);
		Assert.isTrue(actorService.findByPrincipal().equals(comment.getPostedBy()));
		
		Comment result=commentRepository.save(comment);
		
		return result;
	}
	
	public void delete(Comment comment){
		Assert.isTrue(actorService.findByPrincipal().equals(comment.getPostedBy()));
		Assert.notNull(comment);
		
		commentRepository.delete(comment);
	}
	
	
	// Other business methods -------------------------------------------------
	
	public void banComment(Comment comment){
		
		Assert.notNull(comment);
		
		comment.setBanned(true);
		
		commentRepository.save(comment);
		
	}
	
	
}
