
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplyService;
import services.CommentService;
import services.CustomerService;
import services.TransactionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services -----------------------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private ApplyService		applyService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TransactionService	transactionService;


	// Constructor --------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Dashboard ----------------------------------------------------------
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		//Level C
		Double ratioOfferPerRequest;
		Double avgTransactionsPerCustomer;
		Double findAvgApplyRequest;
		Double findAvgApplyOffer;
		Customer customerWithMostAcceptedApplies;
		Collection<Customer> findCustomerWithMostDeniedApplications;
		//Level B
		Double findAvgPerCommentable;
		Double avgCommentsPerActor;
		Collection<Actor> find10PercentAvgCommentsPerActor;
		//Level A
		Double[] minAvMaxMessagesPerActor;
		Double[] minAvMaxMessagesReceivedPerActor;
		Collection<Actor> findActorWithMostMessagesSent;
		Collection<Actor> actorMoreGotMessages;

		//Level C
		ratioOfferPerRequest = this.transactionService.ratioOfferPerRequest();
		avgTransactionsPerCustomer = this.customerService.avgTransactionsPerCustomer();
		findAvgApplyRequest = this.applyService.findAvgApplyRequest();
		findAvgApplyOffer = this.applyService.findAvgApplyOffer();
		customerWithMostAcceptedApplies = this.customerService.customerWithMostAcceptedApplies();
		findCustomerWithMostDeniedApplications = this.customerService.findCustomerWithMostDeniedApplications();
		//Level B
		findAvgPerCommentable = this.commentService.findAvgPerCommentable();
		avgCommentsPerActor = this.actorService.avgCommentsPerActor();
		find10PercentAvgCommentsPerActor = this.actorService.find10PercentAvgCommentsPerActor();
		//Level A
		minAvMaxMessagesPerActor = this.actorService.minAvMaxMessagesPerActor();
		minAvMaxMessagesReceivedPerActor = this.actorService.minAvMaxMessagesReceivedPerActor();
		findActorWithMostMessagesSent = this.actorService.findActorWithMostMessagesSent();
		actorMoreGotMessages = this.actorService.actorMoreGotMessages();

		result = new ModelAndView("administrator/dashboard");
		//Level C
		result.addObject("ratioOfferPerRequest", ratioOfferPerRequest);
		result.addObject("avgTransactionsPerCustomer", avgTransactionsPerCustomer);
		result.addObject("findAvgApplyRequest", findAvgApplyRequest);
		result.addObject("findAvgApplyOffer", findAvgApplyOffer);
		result.addObject("customerWithMostAcceptedApplies", customerWithMostAcceptedApplies);
		result.addObject("findCustomerWithMostDeniedApplications", findCustomerWithMostDeniedApplications);
		//Level B
		result.addObject("findAvgPerCommentable", findAvgPerCommentable);
		result.addObject("avgCommentsPerActor", avgCommentsPerActor);
		result.addObject("find10PercentAvgCommentsPerActor", find10PercentAvgCommentsPerActor);

		//Level A
		result.addObject("minAvMaxMessagesPerActor", minAvMaxMessagesPerActor);
		result.addObject("minAvMaxMessagesReceivedPerActor", minAvMaxMessagesReceivedPerActor);
		result.addObject("findActorWithMostMessagesSent", findActorWithMostMessagesSent);
		result.addObject("actorMoreGotMessages", actorMoreGotMessages);

		return result;
	}
}
