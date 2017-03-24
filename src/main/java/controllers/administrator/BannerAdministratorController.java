
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import controllers.AbstractController;
import domain.Banner;

@Controller
@RequestMapping("/banner/administrator")
public class BannerAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	// Constructors -----------------------------------------------------------

	public BannerAdministratorController() {
		super();
	}

	//	// Listing ----------------------------------------------------------------
	//
	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//		ModelAndView result;
	//		Collection<Banner> banners;
	//
	//		banners = this.bannerService.findAll();
	//
	//		result = new ModelAndView("banner/list");
	//		result.addObject("banners", banners);
	//
	//		return result;
	//	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(String picture) {
		ModelAndView result;
		Banner banner;

		banner = this.bannerService.create(picture);

		result = new ModelAndView("banner/edit");
		result.addObject("banner", banner);
		result.addObject("requestURI", "banner/administrator/edit.do");

		return result;

	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Collection<Banner> banners;

		banners = this.bannerService.findAll();

		result = this.createEditModelAndView((Banner) banners.toArray()[0]);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Banner banner, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(banner);
		} else
			try {
				this.bannerService.save(banner);
				result = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(banner, "request.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Banner banner, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());

			result = this.createEditModelAndView(banner);
		} else
			try {
				this.bannerService.delete(banner);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());

				result = this.createEditModelAndView(banner, "request.commit.error");
			}

		return result;

	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Banner banner) {
		ModelAndView result;

		result = this.createEditModelAndView(banner, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Banner banner, final String message) {
		ModelAndView result;

		result = new ModelAndView("banner/edit");
		result.addObject("banner", banner);
		result.addObject("message", message);
		result.addObject("requestURI", "banner/administrator/edit.do");

		return result;
	}

}
