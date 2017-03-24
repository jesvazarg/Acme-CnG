
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageEmail;

@Repository
public interface MessageEmailRepository extends JpaRepository<MessageEmail, Integer> {

	@Query("select m from MessageEmail m where m.folder.id=?1")
	Collection<MessageEmail> findMessagesByFolderId(int folderId);

}
