
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
import domain.MessageEmail;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageEmailServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageEmailService	messageService;

	@Autowired
	private ActorService		actorService;


	// Tests ------------------------------------------------------------------

	//NOTA IMPORTANTE
	//SE HA DECIDIDO NO USAR EL ESQUEMA VISTO EN TEORIA YA QUE PERDERIAMOS DEMASIADO TIEMPO
	//EN SU IMPLEMENTACION. 
	// Level A
	// Enviar y mostrar mensajes entre usuarios registrados.

	@Test
	public void testMostrarMensaje() {
		super.authenticate("customer1");
		MessageEmail message;

		message = this.messageService.findOne(80);

		System.out.println("findOne: " + message.getId() + "title: " + message.getTitle() + message.getText());
		System.out.println("----------------------------------------");
		super.authenticate(null);
	}

	@Test
	public void testEnviarMensajes() {
		super.authenticate("customer1");

		MessageEmail message;
		Actor recipient;

		message = this.messageService.create();
		recipient = this.actorService.findOne(55);
		final Collection<String> attachments = new ArrayList<String>();

		message.setTitle("Example title");
		message.setText("Example text");
		message.setAttachments(attachments);
		message.setRecipient(recipient);

		Assert.notNull(message);

		message = this.messageService.save(message);

		final Collection<MessageEmail> all = this.messageService.findAll();

		Assert.isTrue(all.contains(message));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMostrarMensajeNegative() {
		MessageEmail message;

		message = this.messageService.findOne(0);

		System.out.println("findOne: " + message.getId() + "title: " + message.getTitle() + message.getText());
		System.out.println("----------------------------------------");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEnviarMensajesNegative() {
		super.authenticate("customer1");

		this.messageService.save(null);

		super.authenticate(null);
	}

	// Responder, reenviar y eliminar mensajes.

	@Test
	public void testResponderAUnMensaje() {
		super.authenticate("customer1");
		final MessageEmail message = this.messageService.findOne(80);
		MessageEmail result = this.messageService.response(message);
		final Collection<String> attachments = new ArrayList<String>();

		result.setText("Example text");
		result.setAttachments(attachments);

		result = this.messageService.save(result);
		final Collection<MessageEmail> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test
	public void testReenviarUnMensaje() {
		super.authenticate("customer1");

		final Collection<String> attachments = new ArrayList<String>();
		final MessageEmail message = this.messageService.findOne(80);
		MessageEmail result = this.messageService.reply(message);
		final Actor actor = this.actorService.findOne(55);
		attachments.addAll(message.getAttachments());

		result.setRecipient(actor);
		result.setAttachments(attachments);

		result = this.messageService.save(result);

		final Collection<MessageEmail> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test
	public void testEliminarUnMensaje() {
		super.authenticate("customer1");

		final MessageEmail message = this.messageService.findOne(80);
		this.messageService.delete(message);

		final Collection<MessageEmail> all = this.messageService.findAll();

		Assert.isTrue(!all.contains(message));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResponderAUnMensajeNegative() {
		super.authenticate("customer1");
		final MessageEmail message = null;
		MessageEmail result = this.messageService.response(message);
		final Collection<String> attachments = new ArrayList<String>();

		result.setText("Example text");
		result.setAttachments(attachments);

		result = this.messageService.save(result);
		final Collection<MessageEmail> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReenviarUnMensajeNegative() {
		super.authenticate("customer1");

		final Collection<String> attachments = new ArrayList<String>();
		final MessageEmail message = null;
		MessageEmail result = this.messageService.reply(message);
		final Actor actor = this.actorService.findOne(43);
		attachments.addAll(message.getAttachments());

		result.setRecipient(actor);
		result.setAttachments(attachments);

		result = this.messageService.save(result);

		final Collection<MessageEmail> all = this.messageService.findAll();

		Assert.isTrue(all.contains(result));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEliminarUnMensajeNegative() {
		super.authenticate("customer3");

		final MessageEmail message = this.messageService.findOne(84);
		this.messageService.delete(message);

		final Collection<MessageEmail> all = this.messageService.findAll();

		Assert.isTrue(!all.contains(message));

		super.authenticate(null);
	}

	@Test
	public void testMostrarMensajesFolder() {
		super.authenticate("customer1");
		Collection<MessageEmail> result = new ArrayList<>();
		result = this.messageService.findMessagesByFolderId(73);
		Assert.isTrue(!result.isEmpty());

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMostrarMensajesFolderNegative() {
		super.authenticate("customer3");
		Collection<MessageEmail> result = new ArrayList<>();
		result = this.messageService.findMessagesByFolderId(73);
		Assert.isTrue(!result.isEmpty());

		super.authenticate(null);
	}

}
