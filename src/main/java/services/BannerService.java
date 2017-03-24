
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------
	@Autowired
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

	public Banner create(final String picture) {
		Banner result;

		result = new Banner();
		result.setPicture(picture);

		return result;
	}

	public Banner save(final Banner banner) {
		Assert.notNull(banner);
		Banner result;

		result = this.bannerRepository.save(banner);

		return result;
	}

	public void delete(final Banner banner) {
		Assert.notNull(banner);

		this.bannerRepository.delete(banner);
	}

	// Other business methods -------------------------------------------------
}
