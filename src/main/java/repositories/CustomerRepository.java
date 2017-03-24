
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccountId);

	//C2: Average number of offers and request per customer.
	//C2: N�mero medio de ofertas y solicitudes por cliente.
	@Query("select avg(c.transactions.size) from Customer c")
	Double avgTransactionsPerCustomer();
}
