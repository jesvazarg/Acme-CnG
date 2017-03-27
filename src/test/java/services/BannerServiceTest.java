
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

	@Test
	public void testFindById() {
		Banner banner;

		banner = this.bannerService.findOne(66);
		Assert.notNull(banner);
	}

	@Test
	public void testFindAll() {
		Collection<Banner> banners;

		banners = this.bannerService.findAll();
		Assert.isTrue(banners.size() == 1);
	}

	@Test
	public void testEditBanner() {
		Collection<Banner> banners;

		banners = this.bannerService.findAll();
		for (Banner b : banners) {
			b.setPicture("http://segurosbaratos.motorgiga.com/uploads/comparador_seguros_de_coche.jpg");
		}
		Assert.isTrue(banners.size() == 1);
	}

}
