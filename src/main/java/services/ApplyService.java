
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplyRepository;
import domain.Apply;
import domain.Customer;
import domain.Transaction;

@Service
@Transactional
public class ApplyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplyRepository		applyRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TransactionService	transactionService;


	// Constructors------------------------------------------------------------
	public ApplyService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Apply findOne(final int applyId) {
		Assert.isTrue(applyId != 0);
		Apply result;

		result = this.applyRepository.findOne(applyId);

		return result;
	}

	public Collection<Apply> findAll() {
		Collection<Apply> results;

		results = this.applyRepository.findAll();

		return results;
	}

	public Apply create(int transactionId) {
		Apply result;
		Transaction t;
		Customer customer;

		t = transactionService.findOne(transactionId);
		customer = customerService.findByPrincipal();

		result = new Apply();
		result.setCustomer(customer);
		result.setStatus("PENDING");
		result.setTransaction(t);

		return result;

	}

	public Apply save(final Apply apply) {
		Assert.notNull(apply);
		Assert.notNull(apply.getCustomer());
		Apply result;

		result = this.applyRepository.save(apply);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<Apply> findByCustomerId(final int customerId) {
		Collection<Apply> result;
		Customer customer;
		customer = this.customerService.findOne(customerId);
		result = this.applyRepository.findByCustomerId(customer.getId());

		return result;
	}

	public Collection<Apply> findApplicationsReceived(final int customerId) {
		Collection<Apply> result;
		Customer customer;
		customer = this.customerService.findOne(customerId);
		result = this.applyRepository.findApplicationsReceived(customer.getId());

		return result;
	}

	public Double findAvgApplyRequest() {
		return this.applyRepository.findAvgApplyRequest();
	}

	public Double findAvgApplyOffer() {
		return this.applyRepository.findAvgApplyOffer();
	}
}
