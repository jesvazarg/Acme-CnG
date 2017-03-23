
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

import services.CustomerService;
import services.OfferService;
import controllers.AbstractController;
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


	// Constructors -----------------------------------------------------------

	public OfferCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Offer> offers;

		offers = this.offerService.findAllNotBanned();

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

		return result;
	}

	// Displaying -------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int offerId) {
		ModelAndView result;
		Offer offer;
		Boolean res = false;

		offer = this.offerService.findOne(offerId);
		res = this.offerService.belongsToCurrentCustomer(offer);

		result = new ModelAndView("offer/display");
		result.addObject("offer", offer);
		result.addObject("isCustomer", res);
		result.addObject("requestURI", "offer/customer/display.do");

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
