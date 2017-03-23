
package services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplyRepository;
import domain.Apply;

@Service
@Transactional
public class ApplyService {

	// Managed repository -----------------------------------------------------

	private ApplyRepository	applyRepository;


	// Supporting services ----------------------------------------------------

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

	public Apply create(final Apply apply) {
		String status;
		Apply result;

		status = new String();

		result = new Apply();
		result.setCustomer(apply.getCustomer());
		result.setStatus(status);
		result.setTransaction(apply.getTransaction());

		return result;

	}

	// Other business methods -------------------------------------------------
}
