package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.CommentableService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.Commentable;


@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController{
	
	
	//Controller-----------------------------------------
	
	public CommentActorController(){
		super();
	}

	//Services --------------------------------------------
	
	@Autowired
	private CommentService commentService;
	
//	@Autowired
//	private ActorService actorService;
	
	@Autowired
	private CommentableService commentableService;
	
	//Create -------------------------------------------------

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(@RequestParam int commentablePostedToId){
		
		ModelAndView result;
		Comment comment;
		
		Commentable commentablePostedTo = commentableService.findById(commentablePostedToId);

		comment = commentService.create(commentablePostedTo);
		
		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/actor/edit.do");
			
		return result;
	}
	
	//Ancillary methods----------------------------------------------------

	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result = createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Comment comment, String message) {
		ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", message);

		return result;
	}

	
}
