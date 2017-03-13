
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	@Query("select f from Folder f where f.actor.id=?1")
	Collection<Folder> findByActorId(int costumerId);

	@Query("select f from Folder f where f.name=?1 and f.actor=?2")
	Folder findByActorAndName(String name, Actor actor);

}
