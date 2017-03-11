
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
