
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CustomerService	customerService;


	// Tests ------------------------------------------------------------------

	//NOTA IMPORTANTE
	//SE HA DECIDIDO NO USAR EL ESQUEMA VISTO EN TEORIA YA QUE PERDERIAMOS DEMASIADO TIEMPO
	//EN SU IMPLEMENTACION. 

	@Test
	public void testFindOne() {
		Customer customer;

		customer = this.customerService.findOne(55);

		System.out.println("findOne: " + customer.getId() + " name: " + customer.getName());
		System.out.println("----------------------------------------");
	}

	//Registrar los usuarios del sistema.

	@Test
	public void testRegistrarUsuarios() {
		Customer customer;
		final UserAccount userAccount;

		customer = this.customerService.create();

		userAccount = customer.getUserAccount();
		userAccount.setUsername("userTest");
		userAccount.setPassword(this.customerService.encryptPassword("userTest"));

		customer.setUserAccount(userAccount);
		customer.setName("Customer test");
		customer.setEmail("customertest@gmail.com");
		customer.setPhoneNumber("+34 (1) 1234");

		customer = this.customerService.save(customer);
		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
		System.out.println("----------------------------------------");
	}

	@Test
	public void testSave() {
		Customer customer;

		customer = this.customerService.findOne(55);

		customer.setName("Customer test");
		customer.setEmail("customertest@gmail.com");
		customer.setPhoneNumber("+34 (1) 1234");

		customer = this.customerService.save(customer);
		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
		System.out.println("----------------------------------------");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveNull() {
		Customer customer;

		customer = null;

		this.customerService.save(customer);
	}

	@Test
	public void testFindByPrincipal() {
		this.authenticate("customer1");
		Customer customer;

		customer = this.customerService.findByPrincipal();

		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
		System.out.println("----------------------------------------");

		this.unauthenticate();
	}

	@Test
	public void testFindByUserAccountId() {
		Customer customer;

		customer = this.customerService.findByUserAccountId(50);

		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
		System.out.println("----------------------------------------");
	}

}
