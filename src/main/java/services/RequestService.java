
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Customer;
import domain.Request;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService		customerService;


	// Constructors------------------------------------------------------------
	public RequestService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Request findOne(final int requestId) {
		Assert.isTrue(requestId != 0);
		Request result;

		result = this.requestRepository.findOne(requestId);

		return result;
	}

	public Collection<Request> findAll() {
		Collection<Request> results;

		results = this.requestRepository.findAll();

		return results;
	}

	public Request create() {
		Request result;
		Customer customer;

		customer = this.customerService.findByPrincipal();

		result = new Request();
		result.setBanned(false);
		result.setCustomer(customer);

		return result;
	}

	public Request save(final Request request) {
		Assert.notNull(request);
		Request result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(customer.equals(request.getCustomer()));

		result = this.requestRepository.save(request);

		return result;
	}

	public void delete(final Request request) {
		Assert.notNull(request);
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(customer.equals(request.getCustomer()));

		this.requestRepository.delete(request);
	}

	// Other business methods -------------------------------------------------

	public Collection<Request> findByCustomerId(final int customerId) {
		Collection<Request> result;
		Customer customer;
		customer = this.customerService.findOne(customerId);
		result = this.requestRepository.findByCustomerId(customer.getId());

		return result;
	}

	public Collection<Request> findByKeywordNotBanned(final String keyword) {
		Collection<Request> result;

		result = this.requestRepository.findByKeywordNotBanned("%" + keyword + "%");

		return result;
	}

	public Collection<Request> findAllNotBanned() {
		Collection<Request> results;

		results = this.requestRepository.findAllNotBanned();

		return results;
	}
}
