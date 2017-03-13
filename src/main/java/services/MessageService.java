
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;


	// Constructors------------------------------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Message findOne(final int messageId) {
		Assert.isTrue(messageId != 0);

		Message result;

		result = this.messageRepository.findOne(messageId);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();

		return result;
	}

	public Message create() {
		Message result;
		Actor actor;
		Calendar calendar;

		result = new Message();
		actor = this.actorService.findByPrincipal();

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setSentMoment(calendar.getTime());
		result.setSender(actor);
		return result;
	}

	public Message save(final Message message) {
		Assert.notNull(message);
		final Message messageSender;
		final Message messageRecipient;
		final Actor sender = this.actorService.findByPrincipal();
		final Actor recipient = this.actorService.findOne(message.getRecipient().getId());

		messageSender = this.messageRepository.save(message);
		//sender.addSentMessage(messageSender);
		//sender.setSentMessages(sender.getSentMessages().add(messageSender));
		this.actorService.save(sender);

		messageRecipient = this.messageRepository.save(message);
		//recipient.addReceivedMessage(messageRecipient);
		//recipient.setReceivedMessages(recipient.getReceivedMessages().add(messageRecipient));
		this.actorService.save(recipient);

		return messageSender;
	}

	public void delete(final Message message) {
		Assert.notNull(message);
		final Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(message.getSender().equals(actor) || message.getRecipient().equals(actor));

		this.messageRepository.delete(message);
	}

	public Message response(final Message message) {
		Assert.notNull(message);

		final Message result = this.create();

		result.setRecipient(message.getSender());
		result.setTitle("Re: " + message.getTitle());

		return result;

	}

}
