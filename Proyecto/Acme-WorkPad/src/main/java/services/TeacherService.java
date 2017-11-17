
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Activity;
import domain.ActivityRecord;
import domain.Assignment;
import domain.BibliographyRecord;
import domain.Folder;
import domain.MailMessage;
import domain.Seminar;
import domain.SocialIdentity;
import domain.Subject;
import domain.Submission;
import domain.Teacher;
import repositories.TeacherRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Transactional
@Service
public class TeacherService {

	@Autowired
	private TeacherRepository	repository;

	@Autowired
	private AssignmentService	assignmentService;

	@Autowired
	private SubjectService		subjectService;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	LoginService				loginService;


	public TeacherService() {
		super();
	}

	//	CRUDs methods --------------------------------

	/**
	 * Crea un nuevo profesor
	 * 
	 * @param actor
	 *            Profesor a crear
	 * @return profesor creado
	 */

	public Teacher listTeacherBySubject(final Integer idSubject) {

		Assert.notNull(idSubject);
		final Subject subject = this.subjectService.findOne(idSubject);
		Assert.notNull(subject);
		return subject.getTeacher();
	}

	public Teacher create() {

		final Teacher res = new Teacher();

		res.setActivitiesRecords(new ArrayList<ActivityRecord>());
		res.setBibliographiesRecords(new ArrayList<BibliographyRecord>());
		res.setEmail("");
		res.setFolders(this.createFolders());
		res.setName("");
		res.setPhone("");
		res.setPostalAddress("");
		res.setSeminars(new ArrayList<Seminar>());
		res.setSocialIdentities(new ArrayList<SocialIdentity>());
		res.setSubjects(new ArrayList<Subject>());
		res.setSurname("");
		final UserAccount account = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority("TEACHER");
		account.setAuthorities(Arrays.asList(auth));
		res.setUserAccount(account);
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
		inbox.setFolderName("inbox");
		inbox.setMessages(new ArrayList<MailMessage>());

		final Folder outbox = new Folder();
		outbox.setFolderName("outbox");
		outbox.setMessages(new ArrayList<MailMessage>());

		final Folder trashbox = new Folder();
		trashbox.setFolderName("trashbox");
		trashbox.setMessages(new ArrayList<MailMessage>());

		final Folder spambox = new Folder();
		spambox.setFolderName("spambox");
		spambox.setMessages(new ArrayList<MailMessage>());

		res.addAll(Arrays.asList(inbox, outbox, trashbox, spambox));

		return res;
	}

	/**
	 * Actualiza un profesor existente
	 * 
	 * @param actor
	 *            Profesor a actualizar
	 * @return profesor actualizado
	 */

	public Teacher update(final Teacher actor) {
		Assert.notNull(actor);
		Teacher m = null;

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
			actor.setFolders(this.folderService.save(this.folderService.createDefaultFolders()));

			m = this.repository.save(actor);
		}
		return m;
	}

	/**
	 * Crea un nuevo profesor
	 * 
	 * @param actor
	 *            Profesor a crear
	 * @return profesor creado
	 */

	public Teacher save(final Teacher actor) {
		Assert.notNull(actor);
		Teacher m = null;

		if (this.exists(actor.getId())) {
			m = this.findOne(actor.getId());
			m.setName(actor.getName());
			m.setEmail(actor.getEmail());
			m.setPhone(actor.getPhone());
			m.setPostalAddress(actor.getPostalAddress());
			m.setSurname(actor.getSurname());
			m.setFolders(actor.getFolders());

			m = this.repository.save(m);
		} else {

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			actor.getUserAccount().setPassword(encoder.encodePassword(actor.getUserAccount().getPassword(), null));
			actor.setFolders(this.folderService.save(this.folderService.createDefaultFolders()));

			m = this.repository.save(actor);
		}
		return m;
	}

	// Others methods ----------------------------------------

	/**
	 * 
	 * @return all subjects that teacher logged is
	 */

	public Collection<Activity> findAllActivitiesBySubject(final Subject subject) {
		final Teacher teacher = this.checkPrincipal();
		this.checkSubjectIsPrincipal(subject);

		final Collection<Activity> activities = this.repository.findAllActivitiesBySubject(subject.getId(), teacher.getId());

		return activities;

	}

	public boolean exists(int assignmentId) {
		return assignmentService.exists(assignmentId);
	}

	/**
	 * Devuelve los Assignments que el profesor tiene en una asignatura que imparte
	 * 
	 * @param subject
	 * @return
	 */

	public Collection<Assignment> listAllAssignment(final Subject subject) {
		this.checkSubjectIsPrincipal(subject);
		final Collection<Assignment> assignments = this.repository.getAssignmentsByTeacherIdSubjectId(this.checkPrincipal().getId(), subject.getId());

		return assignments;
	}
	/**
	 * Almacena un Assignment en una asignatura impartida por el profesor
	 * 
	 * @param subject
	 * @return
	 */

	public Assignment saveAssignment(final Subject subject, final Assignment assignment) {
		Assert.notNull(subject);
		this.checkSubjectIsPrincipal(subject);

		final Assignment saved = this.assignmentService.save(assignment);
		subject.getAssigments().add(saved);

		this.subjectService.update(subject);

		return saved;

	}

	public Assignment updateAssignment(final Subject subject, final Assignment assignment) {
		Assert.notNull(subject);
		Assert.notNull(assignment);
		this.checkSubjectIsPrincipal(subject);

		final Assignment saved = this.assignmentService.update(assignment);
		return saved;
	}

	/**
	 * Elimina un Assignment de una asignatura impartida por el profesor logueado
	 * 
	 * @param assignment
	 * @param subject
	 */
	public void deleteAssignement(final Assignment assignment, final Subject subject) {
		Assert.notNull(assignment);
		this.checkSubjectIsPrincipal(subject);

		subject.getAssigments().remove(assignment);
		this.subjectService.update(subject);

		this.assignmentService.delete(assignment);
	}

	/**
	 * Añade una puntuación a un submission
	 * 
	 * @param submission
	 * @param value
	 */

	public void valorateSubmission(final Submission submission, final Integer value) {
		Assert.notNull(submission);
		this.checkIsPrincipal(submission);

		submission.setGrade(value);
		submission.setMark(value * 0.1);
		this.submissionService.save(submission);
	}

	// Help methods

	/**
	 * Comprueba que el profesor logueado puede valorar este envío
	 * 
	 * @param submission
	 */

	private void checkIsPrincipal(final Submission submission) {
		// TODO Auto-generated method stub
		Assert.isTrue(this.existAssignmentByTeacherIdSubmissionId(this.checkPrincipal().getId(), submission.getId()));
	}

	private boolean existAssignmentByTeacherIdSubmissionId(final int teacherId, final int submissionId) {
		// TODO Auto-generated method stub
		boolean sw = false;
		final Assignment a = this.repository.getAssignmentByTeacherIdSubmissionId(teacherId, submissionId);

		if (a != null)
			sw = true;

		return sw;
	}
	/**
	 * Comprueba que el logueado es profesor
	 * 
	 * @return profesor logueado
	 */

	public Teacher checkPrincipal() {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		Assert.isTrue(LoginService.hasRole("TEACHER"));
		final UserAccount account = LoginService.getPrincipal();
		Assert.isTrue(account.getAuthorities().iterator().next().getAuthority().equals("TEACHER"));

		return this.repository.getPrincipal(account.getId());
	}
	/**
	 * Comprueba que a asignatura es impartida por el profesor
	 * 
	 * @param subject
	 */

	private void checkSubjectIsPrincipal(final Subject subject) {
		// TODO Auto-generated method stub
		final Teacher teacher = this.checkPrincipal();
		Assert.isTrue(teacher.getSubjects().contains(subject));
	}

	public List<Teacher> findAll() {
		return this.repository.findAll();
	}

	public Teacher findOne(final int teacherID) {
		Assert.notNull(teacherID);
		Assert.isTrue(this.repository.exists(teacherID));
		return this.repository.findOne(teacherID);
	}

	public Collection<Subject> listSubjectsByTeacher(final Integer idTeacher) {
		// TODO Auto-generated method stub
		Assert.notNull(idTeacher);
		Assert.isTrue(this.repository.exists(idTeacher));
		final Teacher teacher = this.repository.findOne(idTeacher);
		return teacher.getSubjects();

	}

	public void editInfo(final Teacher teacher) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.hasRole("TEACHER"));
		Assert.isTrue(LoginService.getPrincipal().getId() == teacher.getUserAccount().getId());
		this.update(teacher);
	}

	public void save2(final Teacher teacher) {
		// TODO Auto-generated method stub
		this.repository.save(teacher);
	}
}
