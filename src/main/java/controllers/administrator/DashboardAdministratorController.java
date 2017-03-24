
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services -----------------------------------------------------------
	@Autowired
	private ActorService	actorService;

	@Autowired
	private CustomerService	customerService;


	// Constructor --------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Dashboard ----------------------------------------------------------
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		//Level C
		Double avgTransactionsPerCustomer;
		//Level B
		Double avgCommentsPerActor;
		//Level A
		Double[] minAvMaxMessagesPerActor;
		Collection<Actor> actorMoreGotMessages;

		//Level C
		avgTransactionsPerCustomer = this.customerService.avgTransactionsPerCustomer();
		//Level B
		avgCommentsPerActor = this.actorService.avgCommentsPerActor();
		//Level A
		minAvMaxMessagesPerActor = this.actorService.minAvMaxMessagesPerActor();
		actorMoreGotMessages = this.actorService.actorMoreGotMessages();

		result = new ModelAndView("administrator/dashboard");
		//Level C
		result.addObject("avgTransactionsPerCustomer", avgTransactionsPerCustomer);
		//Level B
		result.addObject("avgCommentsPerActor", avgCommentsPerActor);
		//Level A
		result.addObject("minAvMaxMessagesPerActor", minAvMaxMessagesPerActor);
		result.addObject("actorMoreGotMessages", actorMoreGotMessages);

		return result;
	}

}
