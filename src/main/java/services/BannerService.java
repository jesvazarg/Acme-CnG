
package services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------
	private BannerRepository	bannerRepository;


	// Supporting services ----------------------------------------------------

	// Constructors------------------------------------------------------------
	public BannerService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------
	public Banner findOne(final int bannerId) {
		Assert.isTrue(bannerId != 0);
		Banner result;

		result = this.bannerRepository.findOne(bannerId);

		return result;
	}

	public Collection<Banner> findAll() {
		Collection<Banner> results;

		results = this.bannerRepository.findAll();

		return results;
	}

	// Other business methods -------------------------------------------------
}
