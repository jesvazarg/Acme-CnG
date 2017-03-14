
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	// B3: The actors who have posted ±10% the average number of comments per actor.
	@Query("select a from Actor a where a.comments.size>=(select avg(a1.comments.size)-(0.1*avg(a1.comments.size)) from Actor a1) and a.comments.size<=(select avg(a2.comments.size)+(0.1*avg(a2.comments.size)) from Actor a2)")
	Collection<Actor> find10PercentAvgCommentsPerActor();

	//A1: The minimum, the average, and the maximum number of messages sent per actor.

	@Query("select min(f.messages.size), avg(f.messages.size), max(f.messages.size) from Actor a join a.folders f where f.name='outBox'")
	Double[] minAvMaxMessagesPerActor();

	//A4: The actors who have got more messages.

	@Query("select a from Actor a join a.folders f where (f.name='inBox') and (f.messages.size=(select max(fo.messages.size) from Folder fo where fo.name='inBox'))")
	Actor actorMoreGotMessages();

}
