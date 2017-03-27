
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Apply;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Integer> {

	//C3-Average number of applications per offer or request.
	//Para Request
	@Query("select avg(r.applies.size) from Request r")
	Double findAvgApplyRequest();

	//Para Offer
	@Query("select avg(o.applies.size) from Offer o")
	Double findAvgApplyOffer();

	@Query("select a from Apply a where a.customer.id=?1")
	Collection<Apply> findByCustomerId(int id);

	@Query("select a from Apply a where a.transaction.customer.id=?1")
	Collection<Apply> findApplicationsReceived(int id);
}
