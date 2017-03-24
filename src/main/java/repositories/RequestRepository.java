
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.customer.id=?1")
	Collection<Request> findByCustomerId(int id);

	@Query("select r from Request r where r.banned=false")
	Collection<Request> findAllNotBanned();

	@Query("select r from Request r where (r.title like ?1 or r.description like ?1 or r.originPlace.address like ?1 or r.destinationPlace.address like ?1) and r.banned=false")
	Collection<Request> findByKeywordNotBanned(String keyword);

}
