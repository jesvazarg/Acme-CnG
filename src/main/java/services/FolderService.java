
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FolderRepository	folderRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


	// Constructors -----------------------------------------------------------
	public FolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Folder findOne(final int folderId) {
		Assert.isTrue(folderId != 0);

		Folder result;

		result = this.folderRepository.findOne(folderId);

		return result;
	}

	public Folder create(final Actor actor) {
		Assert.notNull(actor);

		Collection<Message> messages;
		Folder result;

		messages = new HashSet<Message>();

		result = new Folder();
		result.setMessages(messages);
		result.setActor(actor);

		return result;
	}

	public Folder save(Folder folder) {
		Assert.notNull(folder);

		folder = this.folderRepository.save(folder);

		return folder;
	}

	// Other business methods -------------------------------------------------

	public Collection<Folder> findByActorId(final int actorId) {
		Assert.isTrue(actorId != 0);

		Collection<Folder> result;

		result = this.folderRepository.findByActorId(actorId);

		return result;
	}

	public Folder findByActorAndName(final String name, final Actor actor) {
		Assert.isTrue(actor.getId() != 0);

		Folder result;

		result = this.folderRepository.findByActorAndName(name, actor);

		return result;
	}

}
