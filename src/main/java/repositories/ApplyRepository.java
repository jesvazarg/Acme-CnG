
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Apply;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Integer> {

	//C3-Average number of applications per offer or request.
	@Query("select avg(f) from Apply a join a.transaction f")
	Double findAvgApplyTransaction();

	@Query("select a from Apply a where a.customer.id=?1")
	Collection<Apply> findByCustomerId(int id);

	@Query("select a from Apply a where a.transaction.customer.id=?1")
	Collection<Apply> findApplicationsReceived(int id);
}
