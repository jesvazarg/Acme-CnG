
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

	// Level A
	// Enviar y mostrar mensajes entre usuarios registrados.

	@Test
	public void testFindOne() {
		Message message;

		message = this.messageService.findOne(61);

		System.out.println("findOne: " + message.getId() + "title: " + message.getTitle() + message.getText());
		System.out.println("----------------------------------------");
	}

	@Test
	public void testCreateSave() {
		super.authenticate("customer1");

		Message message;
		Actor recipient;

		message = this.messageService.create();
		recipient = this.actorService.findOne(46);
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
	public void testFindOneNegative() {
		Message message;

		message = this.messageService.findOne(0);

		System.out.println("findOne: " + message.getId() + "title: " + message.getTitle() + message.getText());
		System.out.println("----------------------------------------");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSaveNegative() {
		super.authenticate("customer1");

		this.messageService.save(null);

		super.authenticate(null);
	}

	// Responder, reenviar y eliminar mensajes.

	@Test
	public void testRespond() {
		super.authenticate("customer1");
		final Message message = this.messageService.findOne(61);
		Message result = this.messageService.response(message);
		final Collection<String> attachments = new ArrayList<String>();

		result.setText("Example text");
		result.setAttachments(attachments);

		result = this.messageService.save(result);
		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test
	public void testReply() {
		super.authenticate("customer1");

		final Collection<String> attachments = new ArrayList<String>();
		final Message message = this.messageService.findOne(61);
		Message result = this.messageService.reply(message);
		final Actor actor = this.actorService.findOne(43);
		attachments.addAll(message.getAttachments());

		result.setRecipient(actor);
		result.setAttachments(attachments);

		result = this.messageService.save(result);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test
	public void testDeleteMessage() {
		super.authenticate("customer1");

		final Message message = this.messageService.findOne(62);
		this.messageService.delete(message);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(!all.contains(message));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRespondNegative() {
		super.authenticate("customer1");
		final Message message = null;
		Message result = this.messageService.response(message);
		final Collection<String> attachments = new ArrayList<String>();

		result.setText("Example text");
		result.setAttachments(attachments);

		result = this.messageService.save(result);
		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReplyNegative() {
		super.authenticate("customer1");

		final Collection<String> attachments = new ArrayList<String>();
		final Message message = null;
		Message result = this.messageService.reply(message);
		final Actor actor = this.actorService.findOne(43);
		attachments.addAll(message.getAttachments());

		result.setRecipient(actor);
		result.setAttachments(attachments);

		result = this.messageService.save(result);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteMessageNegative() {
		super.authenticate("customer1");

		final Message message = this.messageService.findOne(61);
		this.messageService.delete(message);

		final Collection<Message> all = this.messageService.findAll();

		Assert.isTrue(!all.contains(message));

		super.authenticate(null);
	}

}
