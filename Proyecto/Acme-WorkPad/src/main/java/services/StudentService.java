
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StudentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ActivityRecord;
import domain.Folder;
import domain.Group;
import domain.MailMessage;
import domain.Seminar;
import domain.SocialIdentity;
import domain.Student;
import domain.Subject;

@Transactional
@Service
public class StudentService {

	@Autowired
	private StudentRepository		repository;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private LoginService			loginService;
	@Autowired
	private SubjectService			subjectService;
	@Autowired
	private SeminarService			seminarService;
	@Autowired
	private ActivityRecordService	activityRecordService;


	public StudentService() {
		super();
	}

	//CRUD Methods

	public Student create() {

		final Student res = new Student();

		res.setActivitiesRecords(new ArrayList<ActivityRecord>());

		res.setSeminars(new ArrayList<Seminar>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setSubjects(new ArrayList<Subject>());

		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority("STUDENT");
		account.setAuthorities(Arrays.asList(auth));
		res.setUserAccount(account);
		res.setGroups(new ArrayList<Group>());
		res.setFolders(this.folderService.save(this.folderService.createDefaultFolders()));

		return res;
	}

	/**
	 * Crea las carpetas por defecto del correo
	 * 
	 * @return Carpetas creadas
	 */
	private List<Folder> createFolders() {
		final List<Folder> res = new ArrayList<Folder>();

		final Folder inbox = new Folder();
		inbox.setFolderName("Inbox");
		inbox.setMessages(new ArrayList<MailMessage>());

		final Folder outbox = new Folder();
		outbox.setFolderName("Outbox");
		outbox.setMessages(new ArrayList<MailMessage>());

		final Folder trashbox = new Folder();
		trashbox.setFolderName("Trashbox");
		trashbox.setMessages(new ArrayList<MailMessage>());

		final Folder spambox = new Folder();
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
		Student m = null;

		if (this.exists(actor.getId())) {
			m = this.findOne(actor.getId());
			m.setName(actor.getName());
			m.setEmail(actor.getEmail());
			m.setPhone(actor.getPhone());
			m.setPostalAddress(actor.getPostalAddress());
			m.setSurname(actor.getSurname());

			m = this.repository.save(m);
		} else {

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			actor.getUserAccount().setPassword(encoder.encodePassword(actor.getUserAccount().getPassword(), null));

			m = this.repository.save(actor);
		}
		return m;
	}

	public List<Student> findAll() {
		return this.repository.findAll();
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
		Student m = null;

		if (this.exists(actor.getId())) {
			m = this.findOne(actor.getId());
			m.setName(actor.getName());
			m.setEmail(actor.getEmail());
			m.setPhone(actor.getPhone());
			m.setPostalAddress(actor.getPostalAddress());
			m.setSurname(actor.getSurname());

			m = this.repository.save(m);
		} else {

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			actor.getUserAccount().setPassword(encoder.encodePassword(actor.getUserAccount().getPassword(), null));
			actor.setFolders(this.folderService.save(this.createFolders()));

			m = this.repository.save(actor);
		}
		return m;
	}

	//Other Methods

	/**
	 * Comprueba que el logueado es profesor
	 * 
	 * @return profesor logueado
	 */

	public Student checkPrincipal() {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		Assert.isTrue(LoginService.hasRole("STUDENT"));
		final UserAccount account = LoginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().iterator().next().getAuthority().equals("STUDENT"));

		return this.repository.getPrincipal(account.getId());
	}

	public boolean exists(final int id) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		return this.repository.exists(id);
	}

	public void delete(final Student student) {
		Assert.notNull(student);

		this.repository.delete(student);
	}

	public Student findOne(final Integer idStudent) {
		// TODO Auto-generated method stub
		Assert.notNull(idStudent);
		Assert.isTrue(this.repository.exists(idStudent));
		return this.repository.findOne(idStudent);
	}

	public void editInfo(final Student student) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.hasRole("STUDENT"));
		Assert.isTrue(LoginService.getPrincipal().getId() == student.getUserAccount().getId());

		this.save(student);
	}

	public void enrolSubject(final Integer idSubject) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		Assert.isTrue(LoginService.hasRole("STUDENT"));

		final Subject subject = this.subjectService.findOne(idSubject);

		Assert.isTrue(!subject.getStudents().contains(this.checkPrincipal()));
		subject.getStudents().add(this.checkPrincipal());
		this.subjectService.update(subject);
	}

	public void goInSeminary(final int seminaryId) {
		// TODO Auto-generated method stub
		final Seminar seminar = this.seminarService.findOne(seminaryId);
		final Student student = this.checkPrincipal();
		Assert.isTrue(!student.getSeminars().contains(seminar));
		Assert.isTrue(seminar.getSeats() > 0);
		student.getSeminars().add(seminar);
		this.update(student);

		seminar.setSeats(seminar.getSeats() - 1);
		this.seminarService.update(seminar);
	}

	public void goOutSeminary(final int seminaryId) {
		// TODO Auto-generated method stub
		final Seminar seminar = this.seminarService.findOne(seminaryId);
		final Student student = this.checkPrincipal();
		Assert.isTrue(student.getSeminars().contains(seminar));

		student.getSeminars().remove(seminar);
		seminar.setSeats(seminar.getSeats() + 1);
		this.update(student);
	}
}
