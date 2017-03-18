/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.CreateActorForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Creation ---------------------------------------------------------------	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreateActorForm createActorForm;
		Customer customer;

		customer = this.customerService.create();
		createActorForm = this.customerService.desreconstructCreate(customer);

		result = this.createEditModelAndView(createActorForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreateActorForm createActorForm, final BindingResult binding) {

		ModelAndView result;
		Customer customer;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = this.createEditModelAndView(createActorForm);

		} else
			try {
				customer = this.customerService.reconstructCreate(createActorForm);
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/welcome/index.do");

			} catch (final Throwable oops) {
				System.out.println(oops);

				result = this.createEditModelAndView(createActorForm, "customer.commit.error");

			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateActorForm createActorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(createActorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateActorForm createActorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("customer/create");
		result.addObject("createActorForm", createActorForm);
		result.addObject("actorForm", "createActorForm");
		result.addObject("requestURI", "customer/create.do");
		result.addObject("message", message);

		return result;
	}
}
