
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
import domain.Apply;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplyServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ApplyService	applyService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindById() {
		Apply apply;

		apply = this.applyService.findOne(67);
		Assert.notNull(apply);
	}

	@Test
	public void testFindAll() {
		Collection<Apply> applications;

		applications = this.applyService.findAll();
		Assert.isTrue(applications.size() == 4);
	}

}
