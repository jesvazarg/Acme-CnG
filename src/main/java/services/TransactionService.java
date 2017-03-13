
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TransactionRepository;
import domain.Transaction;

@Service
@Transactional
public class TransactionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TransactionRepository	transactionRepository;


	// Supporting services ----------------------------------------------------

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

	// Other business methods -------------------------------------------------
}
