
package services;

import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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


	// ESQUEMA PROPORCIONADO POR LOS PROFESORES
	//Con este esquema vamos a comprobar la funcion de delete de la entidad offer

	@Test
	public void driverDeleteOffer() {
		final Object testingData[][] = {
			{
				"customer1", 56, null
			}, {
				"customer1", 57, null
			}, {
				"customer1", 58, null
			}, {
				"customer1", 61, IllegalArgumentException.class
			}, {
				"null", 58, IllegalArgumentException.class
			}, {
				"null", 61, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteOfferTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//Con este esquema vamos a comprobar la funcion de editar de la entidad offer

	@Test
	public void driverEditOffer() {
		final Object testingData[][] = {
			{
				"customer1", 56, "Holis", null
			}, {
				"customer1", 57, "Holis", null
			}, {
				"customer2", 59, "Soy el mas mejor", null
			}, {
				"customer2", 59, "", ConstraintViolationException.class
			}, {
				"customer1", 58, null, ConstraintViolationException.class
			}, {
				"customer1", 61, "pepote", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editOfferTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void deleteOfferTemplate(final String username, final int idOffer, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			final Offer offer = this.offerService.findOne(idOffer);
			this.offerService.delete(offer);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void editOfferTemplate(final String username, final int idOffer, final String text, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);
			final Offer offer = this.offerService.findOne(idOffer);
			offer.setTitle(text);
			this.offerService.save(offer);
			this.offerService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

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
