
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import domain.Offer;

@Service
@Transactional
public class OfferService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OfferRepository	offerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors------------------------------------------------------------
	public OfferService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Offer findOne(final int offerId) {
		Assert.isTrue(offerId != 0);
		Offer result;

		result = this.offerRepository.findOne(offerId);

		return result;
	}

	public Collection<Offer> findAll() {
		Collection<Offer> results;

		results = this.offerRepository.findAll();

		return results;
	}
	// Other business methods -------------------------------------------------
}
