
package services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.MailMessage;
import domain.Priority;
import domain.SpamWord;
import repositories.MailMessageRepository;
import security.LoginService;
import security.UserAccount;

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

	public void send(String subject, String body, String priority, String recipient) {
		try {
			MailMessage mailMessage = new MailMessage();
			mailMessage.setSubject(subject);
			mailMessage.setSent(new Date());
			mailMessage.setBody(body);

			Priority prio = new Priority();
			prio.setValue(priority);

			mailMessage.setPriority(prio);

			Actor from = mailMessageRepository.selectActorByMail(recipient, recipient);

			Assert.notNull(from);

			mailMessage.setRecipient(from);

			UserAccount userAccount = LoginService.getPrincipal();
			Actor by = mailMessageRepository.selectSelf(userAccount.getUsername());

			mailMessage.setSender(by);

			MailMessage saved = mailMessageRepository.save(mailMessage);

			for (Folder e : by.getFolders()) {
				if (e.getFolderName().equalsIgnoreCase("outbox")) {
					e.getMessages().add(saved);
					folderService.save(e);

					break;
				}
			}

			boolean is_spam = false;
			String lower = body.toLowerCase();

			for (SpamWord e : spamWordService.findAll()) {
				if (lower.contains(e.getName().toLowerCase())) {
					is_spam = true;
					break;
				}
			}

			MailMessage saved_2 = mailMessageRepository.save(mailMessage);

			for (Folder e : from.getFolders()) {
				if (e.getFolderName().equalsIgnoreCase("Inbox") && !is_spam) {
					e.getMessages().add(saved_2);
					folderService.save(e);

					break;
				}

				if (e.getFolderName().equalsIgnoreCase("Spambox") && is_spam) {
					e.getMessages().add(saved_2);
					folderService.save(e);

					break;
				}
			}

		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	public void moveTo(MailMessage m, Folder f) {
		Actor a = loginService.selectSelf();
		List<Folder> folders = a.getFolders();

		Folder in = null;

		for (Folder e : folders) {
			if (e.getMessages().contains(m)) {
				in = e;
				break;
			}
		}

		if (in != null) {
			in.getMessages().remove(m);
			List<MailMessage> messages = new LinkedList<MailMessage>(f.getMessages());
			messages.add(m);

			folderService.save(in);
			f.setMessages(messages);

			folderService.save(f);
		}
	}

	public void delete(Iterable<MailMessage> entities) {
		mailMessageRepository.delete(entities);
	}

	public MailMessage create() {
		MailMessage message = new MailMessage();

		message.setBody(new String());

		Priority priority = new Priority();
		priority.setValue("NEUTRAL");

		message.setPriority(priority);
		message.setRecipient(administratorService.create());
		message.setSender(administratorService.create());
		message.setSent(new Date());

		return message;
	}

	public void delete(MailMessage entity) {
		Assert.notNull(entity);
		Actor a = loginService.selectSelf();
		List<Folder> folders = a.getFolders();

		Folder folder = null;

		for (Folder e : folders) {
			if (e.getMessages().contains(entity)) {
				folder = e;
				break;
			}
		}

		Assert.notNull(folder);

		if (folder.getFolderName().equalsIgnoreCase("Trashbox")) {
			folder.getMessages().remove(entity);

			folderService.save(folder);
			mailMessageRepository.delete(entity);
		} else {
			for (Folder e : folders) {
				if (e.getFolderName().equalsIgnoreCase("Trashbox")) {
					folder.getMessages().remove(entity);
					folderService.save(folder);
					folderService.flush();

					e.getMessages().add(entity);
					folderService.save(e);
					folderService.flush();

					break;
				}
			}
		}
	}

	public List<MailMessage> findAll() {
		return mailMessageRepository.findAll();
	}

	public MailMessage findOne(Integer arg0) {
		Assert.notNull(arg0);

		return mailMessageRepository.findOne(arg0);
	}

	public List<MailMessage> save(List<MailMessage> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());

		return mailMessageRepository.save(entities);
	}

	public MailMessage save(MailMessage arg0) {
		Assert.notNull(arg0);

		return mailMessageRepository.save(arg0);
	}

	//Other Methods

	public void flush() {
		mailMessageRepository.flush();
	}

	public List<Folder> messagesByFolder(int folder_id) {
		Assert.notNull(folder_id);

		return mailMessageRepository.messagesByFolder(folder_id);
	}
}
