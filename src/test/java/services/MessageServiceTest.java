
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	// Tests ------------------------------------------------------------------

	@Test
	public void testFindOne() {
		Message message;

		message = this.messageService.findOne(29);

		System.out.println("findOne: " + message.getId() + "title: " + message.getTitle() + message.getText());
		System.out.println("----------------------------------------");
	}

	@Test
	public void testFindAll() {
		Collection<Message> messages;

		messages = this.messageService.findAll();

		System.out.println("findAll:");
		for (final Message message : messages)
			System.out.println("findOne: " + message.getId() + "title: " + message.getTitle() + message.getText());
		System.out.println("----------------------------------------");

	}

	@Test
	public void testCreate() {
		super.authenticate("customer1");

		Message message;
		Actor recipient;

		message = this.messageService.create();
		recipient = this.actorService.findOne(27);
		final Collection<String> attachments = new ArrayList<String>();

		message.setTitle("Example title");
		message.setText("Example text");
		message.setAttachments(attachments);
		message.setRecipient(recipient);

		Assert.notNull(message);

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateNegative() {
		super.authenticate("customer1");

		Message message;
		Actor recipient;

		message = this.messageService.create();
		recipient = this.actorService.findOne(27);
		final Collection<String> attachments = new ArrayList<String>();

		message.setTitle("Example title");
		//message.setText("Example text");
		message.setAttachments(attachments);
		message.setRecipient(recipient);

		Assert.notNull(message);

		super.authenticate(null);
	}

	@Test
	public void testCreateSave() {
		super.authenticate("customer1");

		Message message;
		Actor recipient;

		message = this.messageService.create();
		recipient = this.actorService.findOne(27);
		final Collection<String> attachments = new ArrayList<String>();

		message.setTitle("Example title");
		message.setText("Example text");
		message.setAttachments(attachments);
		message.setRecipient(recipient);

		Assert.notNull(message);

		message = this.messageService.save(message);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(all.contains(message));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSaveNegative() {
		super.authenticate("customer1");

		Message message;
		Actor recipient;

		message = this.messageService.create();
		recipient = this.actorService.findOne(27);
		final Collection<String> attachments = new ArrayList<String>();

		message.setTitle("Example title");
		//message.setText("Example text");
		message.setAttachments(attachments);
		message.setRecipient(recipient);

		Assert.notNull(message);

		message = this.messageService.save(message);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(all.contains(message));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("customer1");

		Message message;
		Collection<Message> all;

		message = this.messageService.findOne(29);

		this.messageService.delete(message);

		all = this.messageService.findAll();

		Assert.isTrue(!all.contains(message), "Message has not been deleted.");
		System.out.println("Delete: Message deleted properly.\n----------------------------------------");

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegative() {
		super.authenticate("customer2");

		Message message;
		Collection<Message> all;

		message = this.messageService.findOne(29);

		this.messageService.delete(message);

		all = this.messageService.findAll();

		Assert.isTrue(!all.contains(message), "Message has not been deleted.");
		System.out.println("Delete: Message deleted properly.\n----------------------------------------");

		super.authenticate(null);
	}

	@Test
	public void testResponseCreate() {
		super.authenticate("customer1");

		Message message;
		message = this.messageService.findOne(29);

		final Message result = this.messageService.response(message);

		final Collection<String> attachments = new ArrayList<String>();

		result.setTitle("Example title");
		result.setText("Example text");
		result.setAttachments(attachments);

		Assert.notNull(result);

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResponseCreateNegative() {
		//super.authenticate("customer1");

		Message message;
		message = this.messageService.findOne(29);

		final Message result = this.messageService.response(message);

		final Collection<String> attachments = new ArrayList<String>();

		result.setTitle("Example title");
		result.setText("Example text");
		result.setAttachments(attachments);

		Assert.notNull(result);

		//super.authenticate(null);
	}

	@Test
	public void testResponseCreateAndSave() {
		super.authenticate("customer1");

		Message message;
		message = this.messageService.findOne(29);

		final Message result = this.messageService.response(message);

		final Collection<String> attachments = new ArrayList<String>();

		result.setTitle("Example title");
		result.setText("Example text");
		result.setAttachments(attachments);

		Assert.notNull(result);

		final Message resultSave = this.messageService.save(result);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(!all.contains(resultSave), "Message has not been deleted.");

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResponseCreateAndSaveNegative() {
		super.authenticate("customer1");

		Message message;
		message = this.messageService.findOne(29);

		final Message result = this.messageService.response(message);

		final Collection<String> attachments = new ArrayList<String>();

		result.setTitle("Example title");
		//result.setText("Example text");
		result.setAttachments(attachments);

		Assert.notNull(result);

		final Message resultSave = this.messageService.save(result);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(!all.contains(resultSave), "Message has not been deleted.");

		super.authenticate(null);
	}

}
