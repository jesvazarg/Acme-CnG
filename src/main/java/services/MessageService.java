
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.MessageEmail;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	// Constructors------------------------------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public MessageEmail findOne(final int messageId) {
		Assert.isTrue(messageId != 0);
		final Actor actor = this.actorService.findByPrincipal();

		MessageEmail result;

		result = this.messageRepository.findOne(messageId);

		Assert.isTrue(result.getFolder().getActor().equals(actor));

		return result;
	}

	public Collection<MessageEmail> findAll() {
		Collection<MessageEmail> result;

		result = this.messageRepository.findAll();

		return result;
	}

	public MessageEmail create() {
		MessageEmail result;
		Actor actor;
		Folder folder;
		Calendar calendar;

		result = new MessageEmail();
		actor = this.actorService.findByPrincipal();
		folder = this.folderService.findByActorAndName("outbox", actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setSentMoment(calendar.getTime());
		result.setSender(actor);
		result.setFolder(folder);
		return result;
	}

	public MessageEmail create(final MessageEmail message) {
		MessageEmail result;

		result = new MessageEmail();
		result.setTitle(message.getTitle());
		result.setText(message.getText());
		result.setSentMoment(message.getSentMoment());
		result.setAttachments(message.getAttachments());
		result.setFolder(message.getFolder());
		result.setSender(message.getSender());
		result.setRecipient(message.getRecipient());

		return result;
	}

	public MessageEmail save(MessageEmail message) {
		Assert.notNull(message);

		MessageEmail messageIn;
		Folder folderIn;
		Folder folderOut;

		messageIn = this.create(message);

		folderIn = this.folderService.findByActorAndName("inbox", message.getRecipient());

		folderOut = this.folderService.findByActorAndName("outbox", message.getSender());

		messageIn.setFolder(folderIn);
		message.setFolder(folderOut);

		message = this.messageRepository.save(message);
		this.messageRepository.save(messageIn);

		return message;
	}

	public void delete(final MessageEmail message) {
		Assert.notNull(message);
		final Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(message.getSender().equals(actor) || message.getRecipient().equals(actor));
		Assert.isTrue(message.getFolder().getActor().equals(actor));

		this.messageRepository.delete(message);
	}

	public MessageEmail response(final MessageEmail message) {
		Assert.notNull(message);

		final MessageEmail result = this.create();

		result.setRecipient(message.getSender());
		result.setTitle("Re: " + message.getTitle());

		return result;

	}

	public MessageEmail reply(final MessageEmail message) {
		Assert.notNull(message);

		final Actor actor = this.actorService.findByPrincipal();
		final MessageEmail result = this.create();

		result.setSender(actor);
		result.setTitle(message.getTitle());
		result.setText(message.getText());
		result.setAttachments(message.getAttachments());

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<MessageEmail> findMessagesByFolderId(final int folderId) {

		Collection<MessageEmail> result;

		result = this.messageRepository.findMessagesByFolderId(folderId);

		return result;

	}

	//Devuelve true si la collection esta vacia o si las URLs contenidas en ellas son URLs validas
	public Boolean validatorURL(final Collection<String> lista) {
		Boolean res = false;
		if (!lista.isEmpty()) {
			for (final String aux : lista)
				if ((aux.subSequence(0, 11).equals("http://www.") || (aux.subSequence(0, 12).equals("https://www."))
					&& ((aux.subSequence(aux.length() - 4, aux.length() - 3).equals(".")) || (aux.subSequence(aux.length() - 3, aux.length() - 2).equals(".")))))
					res = true;
				else {
					res = false;
					break;
				}
		} else
			res = true;

		return res;
	}

}
