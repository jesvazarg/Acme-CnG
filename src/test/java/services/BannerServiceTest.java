
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Banner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BannerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	// Tests ------------------------------------------------------------------

	//NOTA IMPORTANTE
	//SE HA DECIDIDO NO USAR EL ESQUEMA VISTO EN TEORIA YA QUE PERDERIAMOS DEMASIADO TIEMPO
	//EN SU IMPLEMENTACION. 

	// Administrar los banners que se mostrarán en la página principal.

	@Test
	public void testFindById() {
		super.authenticate("customer1");
		Banner banner;

		banner = this.bannerService.findOne(66);
		Assert.notNull(banner);

		System.out.println("Id request: " + banner.getId() + " Picture: " + banner.getPicture());
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	@Test
	public void testFindAll() {
		super.authenticate("customer1");
		Collection<Banner> banners;

		banners = this.bannerService.findAll();
		Assert.isTrue(banners.size() == 1);

		this.unauthenticate();
	}

	@Test
	public void testEditBanner() {
		super.authenticate("admin");
		Collection<Banner> banners;

		banners = this.bannerService.findAll();
		for (final Banner banner : banners) {
			System.out.println("Id request: " + banner.getId() + " Picture antes: " + banner.getPicture());
			banner.setPicture("http://segurosbaratos.motorgiga.com/uploads/comparador_seguros_de_coche.jpg");
			bannerService.save(banner);
			System.out.println("Id request: " + banner.getId() + " Picture después: " + banner.getPicture());
			System.out.println("----------------------------------------");
		}

		Assert.isTrue(banners.size() == 1);
		this.unauthenticate();
	}

	@Test(expected = NullPointerException.class)
	public void testEditBannerNegative() {
		super.authenticate("admin");
		Banner banner;

		banner = this.bannerService.findOne(67);
		banner.setPicture("");
		bannerService.save(banner);

		this.unauthenticate();
	}

}
