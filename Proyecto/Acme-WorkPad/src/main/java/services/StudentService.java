
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.ActivityRecord;
import domain.Folder;
import domain.GroupSubject;
import domain.MailMessage;
import domain.Seminar;
import domain.SocialIdentity;
import domain.Student;
import domain.Subject;
import repositories.StudentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class StudentService {

	@Autowired
	private StudentRepository	repository;

	@Autowired
	private FolderService		folderService;


	public StudentService() {
		super();
	}

	//CRUD Methods

	public Student create() {

		Student res = new Student();

		res.setActivitiesRecords(new ArrayList<ActivityRecord>());
		res.setEmail("");
		res.setFolders(createFolders());
		res.setName("");
		res.setPhone("");
		res.setPostalAddress("");
		res.setSeminars(new ArrayList<Seminar>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setSubjects(new ArrayList<Subject>());
		res.setSurname("");
		UserAccount account = new UserAccount();
		Authority auth = new Authority();
		auth.setAuthority("STUDENT");
		account.setAuthorities(Arrays.asList(auth));
		res.setUserAccount(account);
		res.setGroups(new ArrayList<GroupSubject>());
		res.setFolders(folderService.save(folderService.createDefaultFolders()));

		return res;
	}

	/**
	 * Crea las carpetas por defecto del correo
	 * 
	 * @return Carpetas creadas
	 */
	private List<Folder> createFolders() {
		List<Folder> res = new ArrayList<Folder>();

		Folder inbox = new Folder();
		inbox.setFolderName("Inbox");
		inbox.setMessages(new ArrayList<MailMessage>());

		Folder outbox = new Folder();
		outbox.setFolderName("Outbox");
		outbox.setMessages(new ArrayList<MailMessage>());

		Folder trashbox = new Folder();
		trashbox.setFolderName("Trashbox");
		trashbox.setMessages(new ArrayList<MailMessage>());

		Folder spambox = new Folder();
		spambox.setFolderName("Spambox");
		spambox.setMessages(new ArrayList<MailMessage>());

		res.addAll(Arrays.asList(inbox, outbox, trashbox, spambox));

		return res;
	}

	/**
	 * Actualiza un estudiante existente
	 * 
	 * @param actor
	 *            Estudiante a actualizar
	 * @return estudiante actualizado
	 */

	public Student update(final Student actor) {

		Assert.notNull(actor);
		Assert.isTrue(this.repository.exists(actor.getId()));

		return this.repository.save(actor);
	}

	/**
	 * Crea un nuevo estudiante
	 * 
	 * @param actor
	 *            Estudiante a crear
	 * @return estudiante creado
	 */

	public Student save(final Student actor) {

		Assert.notNull(actor);

		Md5PasswordEncoder enc = new Md5PasswordEncoder();
		actor.getUserAccount().setPassword(enc.encodePassword(actor.getUserAccount().getPassword(), null));

		return this.repository.save(actor);
	}

	//Other Methods

	/**
	 * Comprueba que el logueado es profesor
	 * 
	 * @return profesor logueado
	 */

	public Student checkPrincipal() {
		// TODO Auto-generated method stub
		final UserAccount account = LoginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().iterator().next().getAuthority().equals("STUDENT"));

		return this.repository.getPrincipal(account.getId());
	}

	public boolean exists(final int id) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		return this.repository.exists(id);
	}

}
