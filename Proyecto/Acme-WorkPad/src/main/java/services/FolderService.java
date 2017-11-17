
package services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.MailMessage;
import domain.Student;
import domain.Teacher;
import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class FolderService {

	@Autowired
	private FolderRepository		folderRepository;

	//Services
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TeacherService			teacherService;

	@Autowired
	private StudentService			studentService;

	@Autowired
	private MailMessageService		mailmessageService;


	public Folder create() {
		Folder folder = new Folder();

		folder.setFolderName(new String());
		folder.setMessages(new ArrayList<MailMessage>());

		return folder;
	}

	public List<Folder> createDefaultFolders() {
		List<Folder> folders = new ArrayList<Folder>();

		Folder inbox = create();
		inbox.setFolderName("Inbox");
		inbox.setMessages(new LinkedList<MailMessage>());

		Folder outbox = create();
		outbox.setFolderName("Outbox");
		outbox.setMessages(new LinkedList<MailMessage>());

		Folder trashbox = create();
		trashbox.setFolderName("Trashbox");
		trashbox.setMessages(new LinkedList<MailMessage>());

		Folder spambox = create();
		spambox.setFolderName("Spambox");
		spambox.setMessages(new LinkedList<MailMessage>());

		folders.add(inbox);
		folders.add(outbox);
		folders.add(trashbox);
		folders.add(spambox);

		return folders;
	}

	public Actor selectByUsername(String username) {
		return folderRepository.selectByUsername(username);
	}

	public Folder saveCreate(Folder folder) {
		Assert.notNull(folder);

		Folder saved = folderRepository.save(folder);
		UserAccount userAccount = LoginService.getPrincipal();

		Actor actor = folderRepository.selectByUsername(userAccount.getUsername());
		actor.getFolders().add(saved);

		if (actor instanceof Student) {
			studentService.save((Student) actor);
		}if (actor instanceof Teacher) {
			teacherService.save((Teacher) actor);

		} else {
			administratorService.save((Administrator) actor);
		}

		return saved;
	}

	public Folder save(Folder entity) {
		return folderRepository.save(entity);
	}

	public List<Folder> save(Iterable<Folder> entities) {
		return folderRepository.save(entities);
	}

	public void delete(Folder entity) {
		Assert.notNull(entity);

		UserAccount userAccount = LoginService.getPrincipal();
		Actor actor = folderRepository.selectByUsername(userAccount.getUsername());
		actor.getFolders().remove(entity);
		if (actor instanceof Student) {
			studentService.save((Student) actor);
		} else if (actor instanceof Teacher) {
			teacherService.save((Teacher) actor);

		} else {
			administratorService.save((Administrator) actor);
		}
		mailmessageService.delete(entity.getMessages());
		folderRepository.delete(entity);
	}

	public void delete(Iterable<Folder> entities) {
		folderRepository.delete(entities);
	}

	public void flush() {
		folderRepository.flush();
	}

	public Folder findOne(Integer id) {
		return folderRepository.findOne(id);
	}

}
