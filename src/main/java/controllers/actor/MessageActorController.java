
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FolderService	folderService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public MessageActorController() {
		super();
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;
		Boolean isRecipient = false;
		final Actor actor = this.actorService.findByPrincipal();

		message = this.messageService.findOne(messageId);

		if (message.getRecipient().equals(actor))
			isRecipient = true;

		result = new ModelAndView("message/display");
		result.addObject("thisMessage", message);
		result.addObject("isRecipient", isRecipient);

		return result;
	}

	// Create ------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final Message message = this.messageService.create();

		result = this.createEditModelAndView(message);

		return result;
	}

	//Ancillary methods----------------------------------------------------

	//Create
	protected ModelAndView createEditModelAndView(final Message message) {
		final ModelAndView result = this.createEditModelAndView(message, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message emailMessage, final String message) {
		ModelAndView result;
		Actor actor;
		Collection<Actor> recipients;

		actor = this.actorService.findByPrincipal();
		recipients = this.actorService.findAll();
		recipients.remove(actor);

		result = new ModelAndView("message/create");
		result.addObject("emailMessage", emailMessage);
		result.addObject("recipients", recipients);
		result.addObject("message", message);
		return result;
	}

}
