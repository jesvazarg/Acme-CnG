
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

	//B2: Average number of comments posted by administrators and customers.
	//B2: Número medio de comentarios por usuarios y administradores.
	@Query("select avg(a.comments.size) from Actor a")
	Double avgCommentsPerActor();

	// B3: The actors who have posted ±10% the average number of comments per actor.
	@Query("select a from Actor a where a.comments.size>=(select avg(a1.comments.size)-(0.1*avg(a1.comments.size)) from Actor a1) and a.comments.size<=(select avg(a2.comments.size)+(0.1*avg(a2.comments.size)) from Actor a2)")
	Collection<Actor> find10PercentAvgCommentsPerActor();

	//A1: The minimum, the average, and the maximum number of messages sent per actor.

	@Query("select min(f.messages.size), avg(f.messages.size), max(f.messages.size) from Actor a join a.folders f where f.name='outBox'")
	Double[] minAvMaxMessagesPerActor();

	//A2: The minimum, the average, and the maximum number of messages received per actor.
	@Query("select min(f.messages.size), avg(f.messages.size), max(f.messages.size) from Actor a join a.folders f where f.name='inBox'")
	Double[] minAvMaxMessagesReceivedPerActor();

	//A3 The actors who have sent more messages.
	@Query("select m.sender from MessageEmail m group by m.sender having count(m)>=ALL(select count(m1) from MessageEmail m1 group by m1.sender)")
	Collection<Actor> findActorWithMostMessagesSent();

	//A4: The actors who have got more messages.

	@Query("select a from Actor a join a.folders f where (f.name='inBox') and (f.messages.size=(select max(fo.messages.size) from Folder fo where fo.name='inBox'))")
	Collection<Actor> actorMoreGotMessages();

}
