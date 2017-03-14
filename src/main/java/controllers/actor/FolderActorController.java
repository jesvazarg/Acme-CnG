
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FolderService	folderService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public FolderActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list/inBox", method = RequestMethod.GET)
	public ModelAndView listInBox() {
		ModelAndView result;
		Actor actor;
		Collection<Message> messages;
		Folder folder;

		actor = this.actorService.findByPrincipal();

		folder = this.folderService.findByActorAndName("inBox", actor);
		messages = folder.getMessages();

		result = new ModelAndView("folder/display");
		result.addObject("messagesFolder", messages);
		result.addObject("requestURI", "folder/actor/list/inBox.do");

		return result;
	}

	@RequestMapping(value = "/list/outBox", method = RequestMethod.GET)
	public ModelAndView listOutBox() {
		ModelAndView result;
		Actor actor;
		Collection<Message> messages;
		Folder folder;

		actor = this.actorService.findByPrincipal();

		folder = this.folderService.findByActorAndName("outBox", actor);
		messages = folder.getMessages();

		result = new ModelAndView("folder/display");
		result.addObject("messagesFolder", messages);
		result.addObject("requestURI", "folder/actor/list/outBox.do");

		return result;
	}

}
