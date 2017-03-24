
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OfferRepository;
import domain.Comment;
import domain.Customer;
import domain.Offer;

@Service
@Transactional
public class OfferService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OfferRepository	offerRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService	customerService;


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

	public Collection<Offer> findAllNotBanned() {
		Collection<Offer> results;

		results = this.offerRepository.findAllNotBanned();

		return results;
	}

	public Offer create() {
		Offer result;
		Customer customer;

		customer = this.customerService.findByPrincipal();

		result = new Offer();
		result.setBanned(false);
		result.setCustomer(customer);
		final Collection<Comment> postedToComments = new ArrayList<Comment>();
		result.setPostedToComments(postedToComments);

		return result;
	}

	public Offer save(final Offer offer) {
		Assert.notNull(offer);
		Offer result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(customer.equals(offer.getCustomer()));

		result = this.offerRepository.save(offer);

		return result;
	}

	public void delete(final Offer offer) {
		Assert.notNull(offer);
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(customer.equals(offer.getCustomer()));

		this.offerRepository.delete(offer);
	}
	// Other business methods -------------------------------------------------

	public Collection<Offer> findByCustomerId(final int customerId) {
		Collection<Offer> result;
		Customer customer;
		customer = this.customerService.findOne(customerId);
		result = this.offerRepository.findByCustomerId(customer.getId());

		return result;
	}

	public Collection<Offer> findByKeywordNotBanned(final String keyword) {
		Collection<Offer> result;

		result = this.offerRepository.findByKeywordNotBanned("%" + keyword + "%");

		return result;
	}

	public Boolean belongsToCurrentCustomer(final Offer offer) {
		Boolean res = false;
		final Customer customer = this.customerService.findByPrincipal();
		if (offer.getCustomer().equals(customer))
			res = true;
		return res;
	}

}
