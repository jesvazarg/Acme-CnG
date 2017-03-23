
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
import domain.MessageEmail;

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
		MessageEmail message;
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

		final MessageEmail message = this.messageService.create();

		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MessageEmail messageEmail, final BindingResult binding) {
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
		final MessageEmail messageRequest = this.messageService.findOne(messageId);

		if (messageRequest.getFolder().getActor().equals(actor)) {
			final MessageEmail message = this.messageService.response(messageRequest);
			result = this.createEditModelAndViewResponse(message);
		} else
			result = new ModelAndView("redirect:../../folder/actor/list/outBox.do");

		return result;
	}

	@RequestMapping(value = "/createResponse", method = RequestMethod.POST, params = "save")
	public ModelAndView saveResponse(@Valid final MessageEmail messageEmail, final BindingResult binding) {
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

	//Reply ------------------------------------------------------------------------
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int messageId) {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		final MessageEmail messageRequest = this.messageService.findOne(messageId);

		if (messageRequest.getFolder().getActor().equals(actor)) {
			final MessageEmail message = this.messageService.reply(messageRequest);
			result = this.createEditModelAndViewReply(message);
		} else
			result = new ModelAndView("redirect:../../folder/actor/list/outBox.do");

		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReply(@Valid final MessageEmail messageEmail, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewReply(messageEmail);
		else
			try {
				this.messageService.save(messageEmail);
				result = new ModelAndView("redirect:../../folder/actor/list/outBox.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewReply(messageEmail, "message.commit.error");
			}

		return result;
	}

	//Delete ------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MessageEmail message, final BindingResult binding) {
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
	protected ModelAndView createEditModelAndView(final MessageEmail message) {
		final ModelAndView result = this.createEditModelAndView(message, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageEmail messageEmail, final String message) {
		ModelAndView result;
		Actor actor;
		Collection<Actor> recipients;

		actor = this.actorService.findByPrincipal();
		recipients = this.actorService.findAll();
		recipients.remove(actor);

		result = new ModelAndView("message/create");
		result.addObject("messageEmail", messageEmail);
		result.addObject("recipients", recipients);
		result.addObject("message", message);
		return result;
	}

	//Response --------------------------------------------------------------------
	protected ModelAndView createEditModelAndViewResponse(final MessageEmail message) {
		final ModelAndView result = this.createEditModelAndViewResponse(message, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewResponse(final MessageEmail messageEmail, final String message) {
		ModelAndView result;

		result = new ModelAndView("message/response");
		result.addObject("messageEmail", messageEmail);
		result.addObject("message", message);
		return result;
	}

	//Reply ------------------------------------------------------------------------
	protected ModelAndView createEditModelAndViewReply(final MessageEmail message) {
		final ModelAndView result = this.createEditModelAndViewReply(message, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewReply(final MessageEmail messageEmail, final String message) {
		ModelAndView result;
		Actor actor;
		Collection<Actor> recipients;

		actor = this.actorService.findByPrincipal();
		recipients = this.actorService.findAll();
		recipients.remove(actor);

		result = new ModelAndView("message/reply");
		result.addObject("messageEmail", messageEmail);
		result.addObject("recipients", recipients);
		result.addObject("message", message);
		return result;
	}

}
