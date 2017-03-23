/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import domain.Actor;
import forms.CreateActorForm;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	// Service ---------------------------------------------------------------		

	@Autowired
	private ActorService	actorService;


	// Constructors ---------------------------------------------------------------		

	public ProfileController() {
		super();
	}

	// Display ---------------------------------------------------------------		

	@RequestMapping(value = "/displayPrincipal", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		Actor principal;
		final Collection<Authority> authorities;
		String authority;
		Boolean isAdmin = false;

		principal = this.actorService.findByPrincipal();

		authorities = principal.getUserAccount().getAuthorities();
		for (final Authority a : authorities) {
			authority = a.getAuthority();
			if (authority.equals("ADMIN"))
				isAdmin = true;
		}

		result = new ModelAndView("profile/display");
		result.addObject("principal", principal);
		result.addObject("profile", principal);
		result.addObject("same", true);
		result.addObject("isAdmin", isAdmin);
		result.addObject("requestURI", "profile/display.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int actorId) {
		final ModelAndView result;
		Actor actor;
		final Actor principal;
		Boolean same = false;
		final Collection<Authority> authorities;
		String authority;
		Boolean isAdmin = false;

		actor = this.actorService.findOne(actorId);

		principal = this.actorService.findByPrincipal();
		if (actor.equals(principal))
			same = true;

		authorities = principal.getUserAccount().getAuthorities();
		for (final Authority a : authorities) {
			authority = a.getAuthority();
			if (authority.equals("ADMIN"))
				isAdmin = true;
		}

		result = new ModelAndView("profile/display");
		result.addObject("principal", principal);
		result.addObject("profile", actor);
		result.addObject("same", same);
		result.addObject("isAdmin", isAdmin);
		result.addObject("requestURI", "profile/display.do?actorId=" + actor.getId());

		return result;
	}
	// Edit ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Actor actor;
		final CreateActorForm form;

		actor = this.actorService.findByPrincipal();
		form = this.actorService.desreconstructProfile(actor);

		result = this.createEditModelAndView(form);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreateActorForm createActorForm, final BindingResult binding) {

		ModelAndView result;
		Actor actor;

		if (binding.hasErrors())
			result = this.createEditModelAndView(createActorForm);
		else
			try {
				actor = this.actorService.reconstructProfile(createActorForm);
				this.actorService.save(actor);
				result = new ModelAndView("redirect:/profile/displayPrincipal.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(createActorForm, "actor.commit.error");

			}
		return result;
	}

	// Ancillary methods ---------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CreateActorForm form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreateActorForm form, final String message) {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		result = new ModelAndView("profile/edit");
		result.addObject("createActorForm", form);
		result.addObject("requestURI", "profile/edit.do");
		result.addObject("message", message);
		result.addObject("profile", actor);

		return result;
	}
}
