
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Apply;
import domain.Comment;
import domain.Customer;
import domain.Message;
import domain.Transaction;
import forms.CreateActorForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Customer findOne(final int customerId) {
		Assert.isTrue(customerId != 0);
		Customer result;

		result = this.customerRepository.findOne(customerId);

		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> results;

		results = this.customerRepository.findAll();

		return results;
	}

	public Customer create() {
		Customer result;
		UserAccount userAccount;
		Authority authority;
		Collection<Comment> comments;
		Collection<Comment> postedOnComments;
		Collection<Message> sentMessages;
		Collection<Message> receivedMessages;
		Collection<Apply> applies;
		Collection<Transaction> transactions;

		userAccount = new UserAccount();
		authority = new Authority();
		comments = new ArrayList<Comment>();
		postedOnComments = new ArrayList<Comment>();
		sentMessages = new ArrayList<Message>();
		receivedMessages = new ArrayList<Message>();
		applies = new ArrayList<Apply>();
		transactions = new ArrayList<Transaction>();

		authority.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(authority);

		result = new Customer();
		result.setUserAccount(userAccount);
		result.setComments(comments);
		result.setPostedToComments(postedOnComments);
		result.setSentMessages(sentMessages);
		result.setReceivedMessages(receivedMessages);
		result.setApplies(applies);
		result.setTransactions(transactions);

		return result;
	}

	public Customer save(Customer customer) {
		Assert.notNull(customer);

		customer = this.customerRepository.save(customer);

		return customer;
	}

	// Other business methods -------------------------------------------------
	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Customer findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Customer result;

		result = this.customerRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Customer reconstructProfile(final CreateActorForm createActorForm) {
		Assert.notNull(createActorForm);
		Customer customer;
		Md5PasswordEncoder encoder;
		String password;

		customer = this.findByPrincipal();

		password = createActorForm.getPassword();

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		customer.getUserAccount().setUsername(createActorForm.getUsername());
		customer.getUserAccount().setPassword(password);
		customer.setName(createActorForm.getName());
		customer.setEmail(createActorForm.getEmail());
		customer.setPhoneNumber(createActorForm.getPhoneNumber());

		return customer;
	}

	public CreateActorForm desreconstructProfile(final Customer customer) {
		Assert.notNull(customer);

		CreateActorForm createActorForm;

		createActorForm = new CreateActorForm();

		createActorForm.setUsername(customer.getUserAccount().getUsername());
		createActorForm.setPassword(customer.getUserAccount().getPassword());
		createActorForm.setName(customer.getName());
		createActorForm.setEmail(customer.getEmail());
		createActorForm.setPhoneNumber(customer.getPhoneNumber());

		return createActorForm;
	}
}
