
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
import services.OfferService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Comment;
import domain.Customer;
import domain.Offer;

@Controller
@RequestMapping("/offer/customer")
public class OfferCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OfferService	offerService;

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private CommentService	commentService;


	// Constructors -----------------------------------------------------------

	public OfferCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Offer> offers;

		final Actor actor = this.actorService.findByPrincipal();
		final Customer customer = this.customerService.findByUserAccountId(actor.getUserAccount().getId());

		result = new ModelAndView("offer/list");
		if (customer == null) {
			offers = this.offerService.findAll();
			result.addObject("offers", offers);
			result.addObject("general", false);

		} else {
			offers = this.offerService.findAllNotBanned();
			result.addObject("offers", offers);
			result.addObject("general", true);
		}
		result.addObject("myOfferOption", false);
		return result;
	}

	// Search -----------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final String keyword) {
		ModelAndView result;
		Collection<Offer> offers;

		offers = this.offerService.findByKeywordNotBanned(keyword);

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("general", true);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listMyOffers", method = RequestMethod.GET)
	public ModelAndView listMyOffers() {
		ModelAndView result;
		Collection<Offer> offers;
		final Customer customer = this.customerService.findByPrincipal();

		offers = this.offerService.findByCustomerId(customer.getId());

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("general", false);

		result.addObject("myOfferOption", true);
		return result;
	}

	// Displaying -------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int offerId) {
		ModelAndView result;
		Offer offer;
		Boolean res = false;
		offer = this.offerService.findOne(offerId);
		boolean isAdmin = false;

		final Actor actor = this.actorService.findByPrincipal();
		final Customer customer = this.customerService.findByUserAccountId(actor.getUserAccount().getId());

		if (actor instanceof Administrator)
			isAdmin = true;

		if (customer != null)
			res = this.offerService.belongsToCurrentCustomer(offer);

		final Collection<Comment> comments = this.commentService.getCommentsFilterBan(offer.getPostedToComments());

		result = new ModelAndView("offer/display");
		result.addObject("offer", offer);
		result.addObject("isAdmin", isAdmin);
		result.addObject("principal", actor);
		result.addObject("isCustomer", res);
		result.addObject("comments", comments);
		result.addObject("requestURI", "offer/customer/display.do");

		return result;
	}

	// Bann -------------------------------------------------------------

	@RequestMapping(value = "/bann", method = RequestMethod.GET)
	public ModelAndView bann(@RequestParam final int offerId) {
		ModelAndView result;
		Collection<Offer> offers;
		Offer offer;

		offer = this.offerService.bannOffer(offerId);
		offers = this.offerService.findAllNotBanned();

		result = new ModelAndView("offer/list");
		result.addObject("offers", offers);
		result.addObject("general", true);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Offer offer;

		offer = this.offerService.create();

		result = new ModelAndView("offer/edit");
		result.addObject("offer", offer);
		result.addObject("requestURI", "offer/customer/edit.do");

		return result;

	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int offerId) {
		ModelAndView result;
		Offer offer;

		offer = this.offerService.findOne(offerId);

		result = this.createEditModelAndView(offer);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Offer offer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(offer);
		} else
			try {
				this.offerService.save(offer);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(offer, "offer.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Offer offer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(offer);
		} else
			try {
				this.offerService.delete(offer);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(offer, "offer.commit.error");
			}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Offer offer) {
		ModelAndView result;

		result = this.createEditModelAndView(offer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Offer offer, final String message) {
		ModelAndView result;

		result = new ModelAndView("offer/edit");
		result.addObject("offer", offer);
		result.addObject("message", message);
		result.addObject("requestURI", "offer/customer/edit.do");

		return result;
	}

}
