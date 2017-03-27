
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
import domain.Apply;
import domain.Customer;
import domain.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplyServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ApplyService		applyService;

	@Autowired
	private TransactionService	transactionService;


	// Tests ------------------------------------------------------------------

	//NOTA IMPORTANTE
	//SE HA DECIDIDO NO USAR EL ESQUEMA VISTO EN TEORIA YA QUE PERDERIAMOS DEMASIADO TIEMPO
	//EN SU IMPLEMENTACION. 

	@Test
	public void testFindById() {
		Apply apply;

		apply = this.applyService.findOne(67);
		Assert.notNull(apply);
	}

	@Test
	public void testFindAll() {
		Collection<Apply> applications;

		applications = this.applyService.findAll();
		Assert.isTrue(applications.size() == 4);
	}

	@Test
	public void testPublishApplyOffer() {
		super.authenticate("customer1");
		Apply apply;
		Transaction transaction;

		transaction = transactionService.findOne(60);

		apply = this.applyService.create(transaction.getId());

		apply.setTransaction(transaction);

		apply = this.applyService.save(apply);
		final Collection<Apply> applications = this.applyService.findAll();
		Assert.isTrue(applications.contains(apply));
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	@Test
	public void testPublishApplyRequest() {
		super.authenticate("customer1");
		Apply apply;
		Transaction transaction;

		transaction = transactionService.findOne(65);

		apply = this.applyService.create(transaction.getId());

		apply.setTransaction(transaction);

		apply = this.applyService.save(apply);
		final Collection<Apply> applications = this.applyService.findAll();
		Assert.isTrue(applications.contains(apply));
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	@Test
	public void testAcceptApply() {
		super.authenticate("customer1");
		Apply apply;

		apply = this.applyService.findOne(68);
		System.out.println(apply.getStatus());
		apply.setStatus("ACCEPTED");

		apply = this.applyService.save(apply);

		System.out.println(apply.getStatus());
		Assert.isTrue(!apply.equals("PENDING"));
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	@Test
	public void testDenyApply() {
		super.authenticate("customer1");
		Apply apply;

		apply = this.applyService.findOne(68);
		System.out.println(apply.getStatus());
		apply.setStatus("DENIED");

		apply = this.applyService.save(apply);

		System.out.println(apply.getStatus());
		Assert.isTrue(!apply.equals("PENDING"));
		System.out.println("----------------------------------------");
		this.unauthenticate();
	}

	//Customer nulo
	@Test(expected = IllegalArgumentException.class)
	public void testEditApplyNegative1() {
		super.authenticate("customer1");
		Apply apply;
		Transaction transaction;
		Customer customer = null;

		transaction = transactionService.findOne(60);

		apply = this.applyService.create(transaction.getId());

		apply.setCustomer(customer);//por defecto introduciría el que haya iniciado sesión

		apply.setTransaction(transaction);

		apply = this.applyService.save(apply);

		System.out.println("----------------------------------------");
		this.unauthenticate();

	}

	//Transaction nula
	@Test(expected = IllegalArgumentException.class)
	public void testEditApplyNegative2() {
		super.authenticate("customer1");
		Apply apply;
		Transaction transaction = null;

		apply = this.applyService.create(transaction.getId());

		apply.setTransaction(transaction);

		apply = this.applyService.save(apply);

		System.out.println("----------------------------------------");
		this.unauthenticate();

	}
}
