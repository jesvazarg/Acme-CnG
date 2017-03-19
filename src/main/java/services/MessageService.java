
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

	@Autowired
	private FolderService		folderService;


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
		Folder folder;
		Calendar calendar;

		result = new Message();
		actor = this.actorService.findByPrincipal();
		folder = this.folderService.findByActorAndName("outbox", actor);

		calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, -10);

		result.setSentMoment(calendar.getTime());
		result.setSender(actor);
		result.setFolder(folder);
		return result;
	}

	public Message create(final Message message) {
		Message result;

		result = new Message();
		result.setTitle(message.getTitle());
		result.setText(message.getText());
		result.setSentMoment(message.getSentMoment());
		result.setAttachments(message.getAttachments());
		result.setFolder(message.getFolder());
		result.setSender(message.getSender());
		result.setRecipient(message.getRecipient());

		return result;
	}

	public Message save(Message message) {
		Assert.notNull(message);

		Message messageIn;
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

	public void delete(final Message message) {
		Assert.notNull(message);
		final Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(message.getSender().equals(actor) || message.getRecipient().equals(actor));
		Assert.isTrue(message.getFolder().getActor().equals(actor));

		this.messageRepository.delete(message);
	}

	public Message response(final Message message) {
		Assert.notNull(message);

		final Message result = this.create();

		result.setRecipient(message.getSender());
		result.setTitle("Re: " + message.getTitle());

		return result;

	}

	public Message reply(final Message message) {
		Assert.notNull(message);

		final Actor actor = this.actorService.findByPrincipal();
		final Message result = this.create();

		result.setSender(actor);
		result.setTitle(message.getTitle());
		result.setText(message.getText());
		result.setAttachments(message.getAttachments());

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<Message> findMessagesByFolderId(final int folderId) {

		Collection<Message> result;

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
