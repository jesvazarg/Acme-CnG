
package services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Request;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	private RequestRepository	requestRepository;


	// Supporting services ----------------------------------------------------

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

	// Other business methods -------------------------------------------------
}
