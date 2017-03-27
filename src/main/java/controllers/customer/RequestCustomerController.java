
package controllers.customer;

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
import services.CommentService;
import services.CustomerService;
import services.RequestService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Comment;
import domain.Customer;
import domain.Request;

@Controller
@RequestMapping("/request/customer")
public class RequestCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RequestService	requestService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CommentService	commentService;


	// Constructors -----------------------------------------------------------

	public RequestCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;

		final Actor actor = this.actorService.findByPrincipal();
		final Customer customer = this.customerService.findByUserAccountId(actor.getUserAccount().getId());

		result = new ModelAndView("request/list");
		if (customer == null) {
			requests = this.requestService.findAll();
			result.addObject("requests", requests);
			result.addObject("general", false);

		} else {
			requests = this.requestService.findAllNotBanned();
			result.addObject("requests", requests);
			result.addObject("general", true);
		}
		return result;
	}

	// Search -----------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Request> requests;

		requests = this.requestService.findByKeywordNotBanned(keyword);

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("general", true);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listMyRequests", method = RequestMethod.GET)
	public ModelAndView listMyOffers() {
		ModelAndView result;
		Collection<Request> requests;
		final Customer customer = this.customerService.findByPrincipal();

		requests = this.requestService.findByCustomerId(customer.getId());

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("general", false);

		return result;
	}

	// Displaying -------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		Boolean res = false;
		Collection<Comment> comments;

		final Actor actor = this.actorService.findByPrincipal();
		final Customer customer = this.customerService.findByUserAccountId(actor.getUserAccount().getId());

		request = this.requestService.findOne(requestId);

		comments = this.commentService.getCommentsFilterBan(request.getPostedToComments());

		if (customer != null)
			res = this.requestService.belongsToCurrentCustomer(request);

		result = new ModelAndView("request/display");
		result.addObject("request", request);
		result.addObject("principal", actor);
		result.addObject("isCustomer", res);
		result.addObject("comments", comments);
		result.addObject("requestURI", "request/customer/display.do");

		return result;
	}

	// Bann -------------------------------------------------------------

	@RequestMapping(value = "/bann", method = RequestMethod.GET)
	public ModelAndView bann(@RequestParam final int requestId) {
		Collection<Request> requests;
		Request request;

		request = this.requestService.bannRequest(requestId);
		requests = this.requestService.findAllNotBanned();

		return this.list();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Request request;

		request = this.requestService.create();

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("requestURI", "request/customer/edit.do");

		return result;

	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;

		request = this.requestService.findOne(requestId);

		result = this.createEditModelAndView(request);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Request request, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(request);
		} else
			try {
				this.requestService.save(request);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(request, "request.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Request request, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(request);
		} else
			try {
				this.requestService.delete(request);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(request, "request.commit.error");
			}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Request request) {
		ModelAndView result;

		result = this.createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Request request, final String message) {
		ModelAndView result;

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("message", message);
		result.addObject("requestURI", "request/customer/edit.do");

		return result;
	}

}
