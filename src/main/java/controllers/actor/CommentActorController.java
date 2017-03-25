
package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.Commentable;
import domain.Offer;
import domain.Request;

@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController {

	//Controller-----------------------------------------

	public CommentActorController() {
		super();
	}


	//Services --------------------------------------------

	@Autowired
	private CommentService		commentService;

	//	@Autowired
	//	private ActorService actorService;

	@Autowired
	private CommentableService	commentableService;


	//Create -------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentablePostedToId) {

		ModelAndView result;
		Commentable postedTo;
		Comment comment;

		postedTo = this.commentableService.findById(commentablePostedToId);

		comment = this.commentService.create(postedTo);

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {

		ModelAndView result=new ModelAndView();

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(comment);
		} else
			try {
				this.commentService.save(comment);
				if(comment.getPostedTo() instanceof Actor){
				    result = new ModelAndView("redirect:../../profile/display.do?actorId=" + comment.getPostedTo().getId());
				}else if(comment.getPostedTo() instanceof Offer){
					result = new ModelAndView("redirect:../../offer/customer/display.do?offerId=" + comment.getPostedTo().getId());
				}else if(comment.getPostedTo() instanceof Request){
					result = new ModelAndView("redirect:../../request/customer/display.do?requestId="+ comment.getPostedTo().getId());
					
				}
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return result;
	}
	
	//Banning------------------------------------------------------------------------

	 @RequestMapping(value = "/ban", method = RequestMethod.POST, params = "ban")
	public ModelAndView ban(@RequestParam final int commentId){
		 	ModelAndView result=new ModelAndView();
		 	Comment comment=commentService.findOne(commentId);
		 	Assert.notNull(comment);
		 	
			this.commentService.banComment(comment);
			
			if(comment.getPostedTo() instanceof Actor){
			    result = new ModelAndView("redirect:../../profile/display.do?actorId=" + comment.getPostedTo().getId());
			}else if(comment.getPostedTo() instanceof Offer){
				result = new ModelAndView("redirect:../../offer/customer/display.do?offerId=" + comment.getPostedTo().getId());
			}else if(comment.getPostedTo() instanceof Request){
				result = new ModelAndView("redirect:../../request/customer/display.do?requestId="+ comment.getPostedTo().getId());
				
			}
						
			return result;
	 }
	
	 //Deleting-------------------------------------------------------------
	 
	 @RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	 public ModelAndView delete(@RequestParam int commentId){
		 ModelAndView result = new ModelAndView();
		 Comment comment = this.commentService.findOne(commentId);
		 
		 try {
				this.commentService.delete(comment);
				
				if(comment.getPostedTo() instanceof Actor){
				    result = new ModelAndView("redirect:../../profile/display.do?actorId=" + comment.getPostedTo().getId());
				}else if(comment.getPostedTo() instanceof Offer){
					result = new ModelAndView("redirect:../../offer/customer/display.do?offerId=" + comment.getPostedTo().getId());
				}else if(comment.getPostedTo() instanceof Request){
					result = new ModelAndView("redirect:../../request/customer/display.do?requestId="+ comment.getPostedTo().getId());
				}
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return result;
	 }
	 
	
	//Ancillary methods----------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		final ModelAndView result = this.createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", message);

		return result;
	}

}
