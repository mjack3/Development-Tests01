
package services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MailMessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.MailMessage;
import domain.Priority;
import domain.SpamWord;

@Service
@Transactional
public class MailMessageService {

	//Repositories

	@Autowired
	private MailMessageRepository	mailMessageRepository;

	//Services

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private SpamWordService			spamWordService;
	@Autowired
	private LoginService			loginService;


	//Constructor

	public MailMessageService() {
		super();
	}

	//CRUD Methods

	public void send(final String subject, final String body, final String priority, final String recipient) {
		try {
			final MailMessage mailMessage = new MailMessage();
			mailMessage.setSubject(subject);
			mailMessage.setSent(new Date());
			mailMessage.setBody(body);

			final Priority prio = new Priority();
			prio.setValue(priority);

			mailMessage.setPriority(prio);

			final Actor from = this.mailMessageRepository.selectActorByMail(recipient, recipient);

			Assert.notNull(from);

			mailMessage.setRecipient(from);

			final UserAccount userAccount = LoginService.getPrincipal();
			final Actor by = this.mailMessageRepository.selectSelf(userAccount.getUsername());

			mailMessage.setSender(by);

			final MailMessage saved = this.mailMessageRepository.save(mailMessage);

			for (final Folder e : by.getFolders())
				if (e.getFolderName().equalsIgnoreCase("outbox")) {
					e.getMessages().add(saved);
					this.folderService.save(e);

					break;
				}

			boolean is_spam = false;
			final String lower = body.toLowerCase();

			for (final SpamWord e : this.spamWordService.findAll())
				if (lower.contains(e.getName().toLowerCase())) {
					is_spam = true;
					break;
				}

			final MailMessage saved_2 = this.mailMessageRepository.save(mailMessage);

			for (final Folder e : from.getFolders()) {
				if (e.getFolderName().equalsIgnoreCase("Inbox") && !is_spam) {
					e.getMessages().add(saved_2);
					this.folderService.save(e);

					break;
				}

				if (e.getFolderName().equalsIgnoreCase("Spambox") && is_spam) {
					e.getMessages().add(saved_2);
					this.folderService.save(e);

					break;
				}
			}

		} catch (final Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	public void moveTo(final MailMessage m, final Folder f) {
		final Actor a = this.loginService.selectSelf();
		final List<Folder> folders = a.getFolders();

		Folder in = null;

		for (final Folder e : folders)
			if (e.getMessages().contains(m)) {
				in = e;
				break;
			}

		if (in != null) {
			in.getMessages().remove(m);
			final List<MailMessage> messages = new LinkedList<MailMessage>(f.getMessages());
			messages.add(m);

			this.folderService.save(in);
			f.setMessages(messages);

			this.folderService.save(f);
		}
	}

	public void delete(final Iterable<MailMessage> entities) {
		this.mailMessageRepository.delete(entities);
	}

	public MailMessage create() {
		final MailMessage message = new MailMessage();

		message.setBody(new String());

		final Priority priority = new Priority();
		priority.setValue("NEUTRAL");

		message.setPriority(priority);
		//		message.setRecipient(administratorService.create());
		//		message.setSender(administratorService.create());
		message.setSent(new Date());

		return message;
	}

	public void delete(final MailMessage entity) {
		Assert.notNull(entity);
		final Actor a = this.loginService.selectSelf();
		final List<Folder> folders = a.getFolders();

		Folder folder = null;

		for (final Folder e : folders)
			if (e.getMessages().contains(entity)) {
				folder = e;
				break;
			}

		Assert.notNull(folder);

		if (folder.getFolderName().equalsIgnoreCase("Trashbox")) {
			folder.getMessages().remove(entity);

			this.folderService.save(folder);
			this.mailMessageRepository.delete(entity);
		} else
			for (final Folder e : folders)
				if (e.getFolderName().equalsIgnoreCase("Trashbox")) {
					folder.getMessages().remove(entity);
					this.folderService.save(folder);
					this.folderService.flush();

					e.getMessages().add(entity);
					this.folderService.save(e);
					this.folderService.flush();

					break;
				}
	}

	public List<MailMessage> findAll() {
		return this.mailMessageRepository.findAll();
	}

	public MailMessage findOne(final Integer arg0) {
		Assert.notNull(arg0);

		return this.mailMessageRepository.findOne(arg0);
	}

	public List<MailMessage> save(final List<MailMessage> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());

		return this.mailMessageRepository.save(entities);
	}

	public MailMessage save(final MailMessage arg0) {
		Assert.notNull(arg0);
		final List<String> actors = this.administratorService.allActorName();
		Assert.isTrue(actors.contains(arg0.getRecipient().getUserAccount().getUsername()));
		Assert.isTrue(actors.contains(arg0.getSender().getUserAccount().getUsername()));
		return this.mailMessageRepository.save(arg0);
	}

	//Other Methods

	public void flush() {
		this.mailMessageRepository.flush();
	}

	public List<Folder> messagesByFolder(final int folder_id) {
		Assert.notNull(folder_id);

		return this.mailMessageRepository.messagesByFolder(folder_id);
	}

}
