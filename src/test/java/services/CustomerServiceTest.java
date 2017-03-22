
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

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				44, "userTest", this.customerService.encryptPassword("userTest"), "Customer test", "customertest@gmail.com", "+34 (1) 1234", null
			}, {
				null, null, null, null, null, null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.testFindOne((int) testingData[i][0], (Class<?>) testingData[i][6]);
			this.testCreateSave((String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
			this.testSave((int) testingData[i][0], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
			this.testFindByPrincipal((Class<?>) testingData[i][6]);
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void testFindOne(final int customerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Customer customer;

			customer = this.customerService.findOne(customerId);

			System.out.println("findOne: " + customer.getId() + " name: " + customer.getName());
			System.out.println("----------------------------------------");
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	protected void testCreateSave(final String username, final String password, final String name, final String email, final String phoneNumber, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Customer customer;
			UserAccount userAccount;

			customer = this.customerService.create();

			userAccount = customer.getUserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);

			customer.setUserAccount(userAccount);
			customer.setName(name);
			customer.setEmail(email);
			customer.setPhoneNumber(phoneNumber);

			customer = this.customerService.save(customer);

			System.out.println("findOne: " + customer.getId() + " name: " + customer.getName());
			System.out.println("----------------------------------------");
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	protected void testSave(final int customerId, final String name, final String email, final String phoneNumber, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			Customer customer;

			customer = this.customerService.findOne(customerId);

			customer.setName(name);
			customer.setEmail(email);
			customer.setPhoneNumber(phoneNumber);

			customer = this.customerService.save(customer);

			System.out.println("findOne: " + customer.getId() + " name: " + customer.getName());
			System.out.println("----------------------------------------");
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	protected void testFindByPrincipal(final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate("customer1");
			Customer customer;

			customer = this.customerService.findByPrincipal();

			System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
			System.out.println("----------------------------------------");

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	// Tests ------------------------------------------------------------------

	//	@Test
	//	public void testFindOne() {
	//		Customer customer;
	//
	//		customer = this.customerService.findOne(44);
	//
	//		System.out.println("findOne: " + customer.getId() + " name: " + customer.getName());
	//		System.out.println("----------------------------------------");
	//	}
	//
	//	@Test
	//	public void testCreateSave() {
	//		Customer customer;
	//		final UserAccount userAccount;
	//
	//		customer = this.customerService.create();
	//
	//		userAccount = customer.getUserAccount();
	//		userAccount.setUsername("userTest");
	//		userAccount.setPassword(this.customerService.encryptPassword("userTest"));
	//
	//		customer.setUserAccount(userAccount);
	//		customer.setName("Customer test");
	//		customer.setEmail("customertest@gmail.com");
	//		customer.setPhoneNumber("+34 (1) 1234");
	//
	//		customer = this.customerService.save(customer);
	//		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
	//		System.out.println("----------------------------------------");
	//	}
	//
	//	@Test
	//	public void testSave() {
	//		Customer customer;
	//
	//		customer = this.customerService.findOne(44);
	//
	//		customer.setName("Customer test");
	//		customer.setEmail("customertest@gmail.com");
	//		customer.setPhoneNumber("+34 (1) 1234");
	//
	//		customer = this.customerService.save(customer);
	//		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
	//		System.out.println("----------------------------------------");
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void testSaveNull() {
	//		Customer customer;
	//
	//		customer = null;
	//
	//		this.customerService.save(customer);
	//	}
	//
	//	@Test
	//	public void testFindByPrincipal() {
	//		this.authenticate("customer1");
	//		Customer customer;
	//
	//		customer = this.customerService.findByPrincipal();
	//
	//		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
	//		System.out.println("----------------------------------------");
	//
	//		this.unauthenticate();
	//	}
	//
	//	@Test
	//	public void testFindByUserAccountId() {
	//		Customer customer;
	//
	//		customer = this.customerService.findByUserAccountId(39);
	//
	//		System.out.println("create+save: " + customer.getId() + " name: " + customer.getName() + " username: " + customer.getUserAccount().getUsername());
	//		System.out.println("----------------------------------------");
	//	}

}
