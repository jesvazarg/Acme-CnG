
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

	@Query("select o from Offer o where o.customer.id=?1")
	Collection<Offer> findByCustomerId(int id);

	@Query("select o from Offer o where o.banned=false")
	Collection<Offer> findAllNotBanned();

}
