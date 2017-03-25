
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query("select t from Transaction t where t.customer.id=?1")
	Collection<Transaction> findByCustomerId(int id);

	//C1:
	@Query("select (select count(o) from Offer o) / count(r) from Request r")
	Double rationOfferPerRequest();
}
