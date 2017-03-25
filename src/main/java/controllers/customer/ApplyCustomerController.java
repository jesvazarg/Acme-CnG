
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

import services.ApplyService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Apply;
import domain.Customer;

@Controller
@RequestMapping("/apply/customer")
public class ApplyCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ApplyService	applyService;

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public ApplyCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Apply> applies;

		applies = this.applyService.findAll();

		result = new ModelAndView("apply/list");
		result.addObject("applies", applies);

		return result;
	}

	@RequestMapping(value = "/listMyApplies", method = RequestMethod.GET)
	public ModelAndView listMyApplies() {
		ModelAndView result;
		Collection<Apply> applies;
		final Customer customer = this.customerService.findByPrincipal();

		applies = this.applyService.findByCustomerId(customer.getId());

		result = new ModelAndView("apply/list");
		result.addObject("applies", applies);
		result.addObject("general", false);

		return result;
	}

	// Displaying -------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applyId) {
		ModelAndView result;
		Apply apply;

		apply = this.applyService.findOne(applyId);

		result = new ModelAndView("apply/display");
		result.addObject("apply", apply);
		result.addObject("requestURI", "apply/customer/display.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "createApply")
	public ModelAndView create(@RequestParam final int transactionId) {
		ModelAndView result;
		Apply apply;

		apply = this.applyService.create(transactionId);
		this.applyService.save(apply);

		result = new ModelAndView("redirect:../../request/customer/list.do");

		return result;

	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;
		Apply apply;

		apply = this.applyService.findOne(requestId);

		result = this.createEditModelAndView(apply);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Apply apply, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(apply);
		} else
			try {
				this.applyService.save(apply);
				result = new ModelAndView("redirect:/apply/customer/list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(apply, "request.commit.error");
			}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Apply apply) {
		ModelAndView result;

		result = this.createEditModelAndView(apply, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Apply apply, final String message) {
		ModelAndView result;

		result = new ModelAndView("apply/edit");
		result.addObject("apply", apply);
		result.addObject("message", message);
		result.addObject("requestURI", "apply/customer/edit.do");

		return result;
	}

}
