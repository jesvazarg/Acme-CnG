
package services;

import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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


	//Mostrar un offer a un actor del sistema

	@Test
	public void testShowOffer() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.findOne(58);
		System.out.println("findOne: " + offer.getId() + "title: " + offer.getTitle());
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShowOfferNegative() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.findOne(0);
		System.out.println("findOne: " + offer.getId() + "title: " + offer.getTitle());
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	// Publicar una oferta

	@Test
	public void testPublishOffer() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("test offer");
		offer.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		offer.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);

		offer = this.offerService.save(offer);
		final Collection<Offer> offers = this.offerService.findAll();
		Assert.isTrue(offers.contains(offer));
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	//Banear un oferta

	@Test
	public void testBanOffer() {
		super.authenticate("admin");
		Offer offer;

		offer = this.offerService.findOne(59);
		this.offerService.bannOffer(offer.getId());
		Assert.isTrue(offer.isBanned() == true);
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	//Buscar ofertas
	@Test
	public void testSearchOffer() {
		super.authenticate("customer2");

		final Collection<Offer> offers = this.offerService.findByKeywordNotBanned("viaje");
		Assert.isTrue(offers.size() == 2);
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	//Publicar una oferta (test negativo)

	//Con oferta null
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative1() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.save(null);

		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Sin customer
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative2() {
		//super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("test offer");
		offer.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		offer.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);
		offer = this.offerService.save(null);

		System.out.println("----------------------------------------");
		//this.unauthenticate();

	}

	//Con admin
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative3() {
		super.authenticate("admin");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("test offer");
		offer.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		offer.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);
		offer = this.offerService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con titulo vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative4() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		//offer.setTitle("");
		offer.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		offer.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);
		offer = this.offerService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con descripcion vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative5() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("testNegative5");
		//offer.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		offer.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);
		offer = this.offerService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con movingPlace vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative6() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("test");
		offer.setDescription("description");

		/*
		 * final Calendar calendar = Calendar.getInstance();
		 * calendar.set(2018, 05, 15, 13, 30, 25);
		 * offer.setMovingMoment(calendar.getTime());
		 */

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		offer.setDestinationPlace(place);
		offer = this.offerService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con origen vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative7() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("test");
		offer.setDescription("description");

		/*
		 * final Calendar calendar = Calendar.getInstance();
		 * calendar.set(2018, 05, 15, 13, 30, 25);
		 * offer.setMovingMoment(calendar.getTime());
		 */

		final Place place = new Place();
		place.setAddress("Badalona");

		//offer.setOriginPlace(place);
		offer.setDestinationPlace(place);
		offer = this.offerService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con destino vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishOfferNegative8() {
		super.authenticate("customer1");
		Offer offer;

		offer = this.offerService.create();

		offer.setTitle("test");
		offer.setDescription("description");

		/*
		 * final Calendar calendar = Calendar.getInstance();
		 * calendar.set(2018, 05, 15, 13, 30, 25);
		 * offer.setMovingMoment(calendar.getTime());
		 */

		final Place place = new Place();
		place.setAddress("Badalona");

		offer.setOriginPlace(place);
		//offer.setDestinationPlace(place);
		offer = this.offerService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

}
