package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;


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
	
	@Autowired
	private ActorService actorService;
	
	//Create -------------------------------------------------

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView  create(@RequestParam int actorPostedToId){
		
		ModelAndView result;
		Comment comment;
		
		Actor actorPostedTo = actorService.findOne(actorPostedToId);
		comment = commentService.create(actorPostedTo);
		
		result = new ModelAndView("comment/actor/edit");
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