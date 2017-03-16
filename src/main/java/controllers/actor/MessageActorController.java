
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message messageEmail, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageEmail);
		else
			try {
				this.messageService.save(messageEmail);
				result = new ModelAndView("redirect:../../folder/actor/list/outBox.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageEmail, "message.commit.error");
			}

		return result;
	}

	// Response ----------------------------------------------------------------

	@RequestMapping(value = "/createResponse", method = RequestMethod.GET)
	public ModelAndView response(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final Message messageRequest = this.messageService.findOne(messageId);

		if (messageRequest.getFolder().getActor().equals(actor)) {
			final Message message = this.messageService.response(messageRequest);
			result = this.createEditModelAndViewResponse(message);
		} else
			result = new ModelAndView("redirect:../../folder/actor/list/outBox.do");

		return result;
	}

	@RequestMapping(value = "/createResponse", method = RequestMethod.POST, params = "save")
	public ModelAndView saveResponse(@Valid final Message messageEmail, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewResponse(messageEmail);
		else
			try {
				this.messageService.save(messageEmail);
				result = new ModelAndView("redirect:../../folder/actor/list/outBox.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewResponse(messageEmail, "message.commit.error");
			}

		return result;
	}

	//Delete------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message message, final BindingResult binding) {
		ModelAndView result;

		try {
			this.messageService.delete(message);
			result = new ModelAndView("redirect:../../folder/actor/list/inBox.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../folder/actor/list/inBox.do");
		}

		return result;
	}

	//Ancillary methods---------------------------------------------------------

	//Create --------------------------------------------------------------------
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

		String title = "";
		String text = "";

		if (emailMessage.getTitle() == "")
			title = "title";

		if (emailMessage.getText() == "")
			text = "text";

		result = new ModelAndView("message/create");
		result.addObject("emailMessage", emailMessage);
		result.addObject("recipients", recipients);
		result.addObject("title", title);
		result.addObject("text", text);
		result.addObject("message", message);
		return result;
	}

	//Response --------------------------------------------------------------------
	protected ModelAndView createEditModelAndViewResponse(final Message message) {
		final ModelAndView result = this.createEditModelAndViewResponse(message, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewResponse(final Message emailMessage, final String message) {
		ModelAndView result;

		String text = "";

		if (emailMessage.getText() == "")
			text = "text";

		result = new ModelAndView("message/response");
		result.addObject("emailMessage", emailMessage);
		result.addObject("text", text);
		result.addObject("message", message);
		return result;
	}

}
