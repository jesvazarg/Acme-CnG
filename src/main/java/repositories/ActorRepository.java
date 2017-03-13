
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	//A1: The minimum, the average, and the maximum number of messages sent per actor.
	@Query("select min(a.sentMessages.size)/2, avg(a.sentMessages.size)/2, max(a.sentMessages.size)/2 from Actor a")
	Double[] minAvMaxMessagesPerActor();
	/*
	 * //A4: The actors who have got more messages.
	 * 
	 * @Query("select a from Actor a where a.receivedMessages.size=(select max(ac.receivedMessages.size) from Actor ac)")
	 * Actor actorMoreGotMessages();
	 */
}
