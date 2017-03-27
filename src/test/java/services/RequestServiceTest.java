
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
import domain.Place;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {

	@Autowired
	private RequestService	requestService;


	//Mostrar un offer 

	@Test
	public void testShowRequest() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.findOne(62);
		System.out.println("Id request: " + request.getId() + " title: " + request.getTitle());
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShowRequestNegative() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.findOne(0);
		System.out.println("Id request: " + request.getId() + " title: " + request.getTitle());
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	// Publicar una oferta

	@Test
	public void testPublishRequest() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.create();

		request.setTitle("test request");
		request.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		request.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Tomares");

		request.setOriginPlace(place);
		request.setDestinationPlace(place);

		request = this.requestService.save(request);
		final Collection<Request> requests = this.requestService.findAll();
		Assert.isTrue(requests.contains(request));
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	//Banear un oferta

	@Test
	public void testBanRequest() {
		super.authenticate("admin");
		Request request;

		request = this.requestService.findOne(64);
		this.requestService.bannRequest(request.getId());
		Assert.isTrue(request.isBanned() == true);
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	//Buscar ofertas
	@Test
	public void testSearchRequest() {
		super.authenticate("customer2");

		final Collection<Request> offers = this.requestService.findByKeywordNotBanned("necesito");
		Assert.isTrue(offers.size() == 2);
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	//Publicar un request (test negativo)

	//Con request null
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative1() {
		super.authenticate("customer1");

		this.requestService.save(null);

		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Sin customer
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative2() {
		//super.authenticate("customer1");
		Request request;

		request = this.requestService.create();

		request.setTitle("test request");
		request.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		request.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Gelves");

		request.setOriginPlace(place);
		request.setDestinationPlace(place);
		request = this.requestService.save(null);

		System.out.println("----------------------------------------");
		//this.unauthenticate();

	}

	//Con admin
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative3() {
		super.authenticate("admin");
		Request request;

		request = this.requestService.create();

		request.setTitle("test request");
		request.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		request.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Lepe");

		request.setOriginPlace(place);
		request.setDestinationPlace(place);
		request = this.requestService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con titulo vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative4() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.create();

		request.setDescription("description");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		request.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Cádiz");

		request.setOriginPlace(place);
		request.setDestinationPlace(place);
		request = this.requestService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con descripcion vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative5() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.create();

		request.setTitle("test request");

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 05, 15, 13, 30, 25);
		request.setMovingMoment(calendar.getTime());

		final Place place = new Place();
		place.setAddress("Alicante");

		request.setOriginPlace(place);
		request.setDestinationPlace(place);
		request = this.requestService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con movingPlace vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative6() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.create();

		request.setTitle("test request");
		request.setDescription("description");

		final Place place = new Place();
		place.setAddress("Madrid");

		request.setOriginPlace(place);
		request.setDestinationPlace(place);
		request = this.requestService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con origen vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative7() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.create();

		request.setTitle("test request");
		request.setDescription("description");

		final Place place = new Place();
		place.setAddress("Murcia");

		//offer.setOriginPlace(place);
		request.setDestinationPlace(place);
		request = this.requestService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Con destino vacio
	@Test(expected = IllegalArgumentException.class)
	public void testPublishRequestNegative8() {
		super.authenticate("customer1");
		Request request;

		request = this.requestService.create();

		request.setTitle("test request");
		request.setDescription("description");

		final Place place = new Place();
		place.setAddress("Zaragoza");

		request.setOriginPlace(place);
		request = this.requestService.save(null);
		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

}
