
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Commentable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentableServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CommentableService	commentableService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindById() {
		final Commentable commentable;

		commentable = this.commentableService.findById(55);
		Assert.notNull(commentable);
	}

	@Test
	public void testFindAll() {
		final Collection<Commentable> commentables;

		commentables = this.commentableService.findAll();
		Assert.isTrue(commentables.size() == 14);
	}
}
