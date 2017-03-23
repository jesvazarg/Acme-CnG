
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Apply;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Integer> {

	//C3-Average number of applications per offer or request.
	@Query("select avg(f) from Apply a join a.transaction f")
	Double findAvgApplyTransaction();
}
