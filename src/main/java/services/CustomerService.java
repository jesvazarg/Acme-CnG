
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
import domain.Folder;
import domain.Transaction;
import forms.CreateActorForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private FolderService		folderService;


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
		Collection<Apply> applies;
		Collection<Transaction> transactions;
		Collection<Folder> folders;

		userAccount = new UserAccount();
		authority = new Authority();
		comments = new ArrayList<Comment>();
		postedOnComments = new ArrayList<Comment>();
		applies = new ArrayList<Apply>();
		transactions = new ArrayList<Transaction>();
		folders = new ArrayList<Folder>();

		authority.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(authority);

		result = new Customer();
		result.setUserAccount(userAccount);
		result.setComments(comments);
		result.setPostedToComments(postedOnComments);
		result.setApplies(applies);
		result.setTransactions(transactions);
		result.setFolders(folders);

		return result;
	}

	public Customer save(Customer customer) {
		Assert.notNull(customer);

		if (customer.getFolders().isEmpty()) {

			Folder inbox;
			Folder outbox;

			inbox = this.folderService.create(customer);
			inbox.setName("inbox");

			outbox = this.folderService.create(customer);
			outbox.setName("outbox");

			customer.addFolder(inbox);
			customer.addFolder(outbox);

			customer = this.customerRepository.save(customer);

			inbox.setActor(customer);
			outbox.setActor(customer);

			inbox = this.folderService.save(inbox);
			outbox = this.folderService.save(outbox);
		} else
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

	public Customer reconstructCreate(final CreateActorForm createActorForm) {
		Assert.notNull(createActorForm);
		Customer customer;
		String password;

		Assert.isTrue(createActorForm.getPassword().equals(createActorForm.getConfirmPassword())); // Comprobamos que las dos contraseñas sean la misma
		Assert.isTrue(createActorForm.getIsAgree()); // Comprobamos que acepte las condiciones

		customer = this.create();
		password = this.encryptPassword(createActorForm.getPassword());

		customer.getUserAccount().setUsername(createActorForm.getUsername());
		customer.getUserAccount().setPassword(password);
		customer.setName(createActorForm.getName());
		customer.setEmail(createActorForm.getEmail());
		customer.setPhoneNumber(createActorForm.getPhoneNumber());

		return customer;
	}

	public CreateActorForm desreconstructCreate(final Customer customer) {
		CreateActorForm createActorForm;

		createActorForm = new CreateActorForm();

		createActorForm.setUsername(customer.getUserAccount().getUsername());
		createActorForm.setName(customer.getName());
		createActorForm.setEmail(customer.getEmail());
		createActorForm.setPhoneNumber(customer.getPhoneNumber());

		return createActorForm;
	}

	public String encryptPassword(String password) {
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);

		return password;
	}

	public Double avgTransactionsPerCustomer() {
		return this.customerRepository.avgTransactionsPerCustomer();
	}

	public Customer customerWithMostAcceptedApplies() {
		Customer customer;
		customer = this.customerRepository.customerWithMostAcceptedApplies();
		return customer;
	}
}
