
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// B1: Average number of comments per actor, offer, or request.
	@Query("select avg(c.comments.size) from Commentable c")
	Double findAvgPerCommentable();
}
