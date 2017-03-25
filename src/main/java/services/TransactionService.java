
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TransactionRepository;
import domain.Apply;
import domain.Customer;
import domain.Transaction;

@Service
@Transactional
public class TransactionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TransactionRepository	transactionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService			customerService;


	// Constructors------------------------------------------------------------
	public TransactionService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Transaction findOne(final int transactionId) {
		Assert.isTrue(transactionId != 0);
		Transaction result;

		result = this.transactionRepository.findOne(transactionId);

		return result;
	}

	public Collection<Transaction> findAll() {
		Collection<Transaction> results;

		results = this.transactionRepository.findAll();

		return results;
	}

	public Transaction create() {
		Transaction result;
		Customer customer;
		Collection<Apply> applies;

		customer = this.customerService.findByPrincipal();
		applies = new ArrayList<Apply>();

		result = new Transaction();
		result.setBanned(false);
		result.setCustomer(customer);
		result.setApplies(applies);

		return result;
	}

	public Transaction save(final Transaction transaction) {
		Assert.notNull(transaction);
		Transaction result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(customer.equals(transaction.getCustomer()));

		result = this.transactionRepository.save(transaction);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<Transaction> findByCustomerId(final int customerId) {
		Collection<Transaction> result;
		Customer customer;
		customer = this.customerService.findOne(customerId);
		result = this.transactionRepository.findByCustomerId(customer.getId());

		return result;
	}

	public Double ratioOfferPerRequest() {
		Double res;
		res = this.transactionRepository.rationOfferPerRequest();
		return res;
	}
}
