
package services;

import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Offer;
import domain.Place;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class OfferServiceTest extends AbstractTest {

	@Autowired
	private OfferService	offerService;

	@Autowired
	private CustomerService	customerService;


	//Mostrar offer

	@Test
	public void testShowOffer() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.findOne(58);
		System.out.println("findOne: " + offer.getId() + "title: " + offer.getTitle());
		System.out.println("----------------------------------------");
		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShowOfferNegative() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.findOne(0);
		System.out.println("findOne: " + offer.getId() + "title: " + offer.getTitle());
		System.out.println("----------------------------------------");
		super.authenticate(null);
	}

	// Crear, guardar, editar y borrar offer

	@Test
	public void testCreateOffer() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("test offer");
		offer.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, +2);
		offer.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);

		offer = this.offerService.save(offer);
		final Collection<Offer> offers = this.offerService.findAll();
		//Assert.isTrue(offers.contains(offer));
		System.out.println("----------------------------------------");
		super.authenticate(null);
	}

	@Test
	public void testEditOffer() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.findOne(58);

		offer.setTitle("test offer");
		offer.setDescription("description");

		final Place place = new Place();
		place.setAddress("Madrid");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);

		offer = this.offerService.save(offer);
		Assert.isTrue(this.offerService.findOne(58).getTitle().equalsIgnoreCase("test offer"));
		System.out.println("----------------------------------------");
		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateOfferNegative() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.save(null);

		System.out.println("----------------------------------------");
		super.authenticate(null);

	}

	//Test negativo con description vacio.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testEditOfferTiNegative() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.findOne(58);

		offer.setTitle("test title");
		offer.setDescription("");

		final Place place = new Place();
		place.setAddress("Madrid");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);

		offer = this.offerService.save(offer);
		System.out.println("----------------------------------------");
		super.authenticate(null);
	}

	//	//Test negativo con titulo vacio.
	//	@Test(expected = IllegalArgumentException.class)
	//	@Rollback(value = true)
	//	public void testEditOfferNegative() {
	//		super.authenticate("customer1");
	//		Offer offer;
	//
	//		offer = this.offerService.findOne(58);
	//
	//		offer.setTitle("");
	//		offer.setDescription("description");
	//
	//		final Place place = new Place();
	//		place.setAddress("Madrid");
	//
	//		offer.setOriginPlace(place);
	//		offer.setDestinationPlace(place);
	//
	//		offer = this.offerService.save(offer);
	//		Assert.isTrue(this.offerService.findOne(58).getTitle().equalsIgnoreCase("test offer"));
	//		System.out.println("----------------------------------------");
	//		super.authenticate(null);
	//	}
	//
	//	//Test negativo con titulo vacio.
	//	@Test(expected = IllegalArgumentException.class)
	//	@Rollback(value = true)
	//	public void testEditOfferNegative() {
	//		super.authenticate("customer1");
	//		Offer offer;
	//
	//		offer = this.offerService.findOne(58);
	//
	//		offer.setTitle("");
	//		offer.setDescription("description");
	//
	//		final Place place = new Place();
	//		place.setAddress("Madrid");
	//
	//		offer.setOriginPlace(place);
	//		offer.setDestinationPlace(place);
	//
	//		offer = this.offerService.save(offer);
	//		Assert.isTrue(this.offerService.findOne(58).getTitle().equalsIgnoreCase("test offer"));
	//		System.out.println("----------------------------------------");
	//		super.authenticate(null);
	//	}
	//
	//	//Test negativo con titulo vacio.
	//	@Test(expected = IllegalArgumentException.class)
	//	@Rollback(value = true)
	//	public void testEditOfferNegative() {
	//		super.authenticate("customer1");
	//		Offer offer;
	//
	//		offer = this.offerService.findOne(58);
	//
	//		offer.setTitle("");
	//		offer.setDescription("description");
	//
	//		final Place place = new Place();
	//		place.setAddress("Madrid");
	//
	//		offer.setOriginPlace(place);
	//		offer.setDestinationPlace(place);
	//
	//		offer = this.offerService.save(offer);
	//		Assert.isTrue(this.offerService.findOne(58).getTitle().equalsIgnoreCase("test offer"));
	//		System.out.println("----------------------------------------");
	//		super.authenticate(null);
	//	}
	//
	//	//Test negativo con titulo vacio.
	//	@Test(expected = IllegalArgumentException.class)
	//	@Rollback(value = true)
	//	public void testEditOfferNegative() {
	//		super.authenticate("customer1");
	//		Offer offer;
	//
	//		offer = this.offerService.findOne(58);
	//
	//		offer.setTitle("");
	//		offer.setDescription("description");
	//
	//		final Place place = new Place();
	//		place.setAddress("Madrid");
	//
	//		offer.setOriginPlace(place);
	//		offer.setDestinationPlace(place);
	//
	//		offer = this.offerService.save(offer);
	//		Assert.isTrue(this.offerService.findOne(58).getTitle().equalsIgnoreCase("test offer"));
	//		System.out.println("----------------------------------------");
	//		super.authenticate(null);
	//	}
	//
	//	//Test negativo con titulo vacio.
	//	@Test(expected = IllegalArgumentException.class)
	//	@Rollback(value = true)
	//	public void testEditOfferNegative() {
	//		super.authenticate("customer1");
	//		Offer offer;
	//
	//		offer = this.offerService.findOne(58);
	//
	//		offer.setTitle("");
	//		offer.setDescription("description");
	//
	//		final Place place = new Place();
	//		place.setAddress("Madrid");
	//
	//		offer.setOriginPlace(place);
	//		offer.setDestinationPlace(place);
	//
	//		offer = this.offerService.save(offer);
	//		Assert.isTrue(this.offerService.findOne(58).getTitle().equalsIgnoreCase("test offer"));
	//		System.out.println("----------------------------------------");
	//		super.authenticate(null);
	//	}

}
