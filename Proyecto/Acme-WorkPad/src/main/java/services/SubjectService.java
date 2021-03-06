
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubjectRepository;
import security.LoginService;
import domain.Activity;
import domain.Administrator;
import domain.Assignment;
import domain.BibliographyRecord;
import domain.Bulletin;
import domain.Group;
import domain.School;
import domain.Student;
import domain.Subject;
import domain.Teacher;

@Transactional
@Service
public class SubjectService {

	@Autowired
	private LoginService			loginService;
	@Autowired
	private SubjectRepository		repository;

	@Autowired
	private StudentService			studentService;

	@Autowired
	private TeacherService			teacherService;

	@Autowired
	private SchoolService			schoolService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActivityRecordService	activityRecordService;


	public SubjectService() {
		super();
	}

	public Collection<Subject> findSubjectsByWordWithoutSeats(final String word) {
		Assert.notNull(word);
		final Collection<Subject> subjects = this.repository.findSubjectsByWordWithoutSeats(word);

		return subjects;
	}

	public Collection<Subject> findSubjectsByWordWithSeats(final String word) {
		Assert.notNull(word);
		final Collection<Subject> subjects = this.repository.findSubjectsByWordWithSeats(word);

		return subjects;
	}

	/**
	 * Actualiza la información de una asignatura
	 * 
	 * @param subject
	 */

	public void update(final Subject subject) {
		// TODO Auto-generated method stub
		Assert.isTrue(this.repository.exists(subject.getId()));
		this.repository.save(subject);
		if (LoginService.hasRole("ADMINISTRATOR"))
			this.activityRecordService.RQNcreateReport("edits.subject");
	}

	/**
	 * Devuelve la asignatura asociada a un profesor y a una actividad
	 * 
	 * @param id
	 * @param id2
	 * @return
	 */

	public Subject findSubjectByTeacherIdActivityId(final int id, final int id2) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		Assert.notNull(id2);

		final Subject subject = this.repository.findSubjectByTeacherIdActivityId(id, id2);
		Assert.notNull(subject);
		return subject;
	}

	/*
	 * karli
	 */

	public List<Subject> findAll() {
		return this.repository.findAll();
	}

	public List<Subject> subjectsByStudents(final int id) {
		return this.repository.subjectsByStudents(id);
	}

	public Subject save(final Subject entity) {
		Assert.notNull(entity);

		Subject aux = new Subject();
		Subject m = null;
		if (this.exists(entity.getId())) {
			if (LoginService.hasRole("TEACHER")) {
				final Teacher actor = (Teacher) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

				Assert.isTrue(actor.getSubjects().contains(entity));
			} else if (LoginService.hasRole("ADMINISTRATOR")) {
				final Administrator actor = (Administrator) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());

				Assert.isTrue(actor.getSubjects().contains(entity));
			}

			aux = this.repository.findOne(entity.getId());
			aux.setTitle(entity.getTitle());
			aux.setTicker(entity.getTicker());
			aux.setSyllabus(entity.getSyllabus());
			aux.setSeats(entity.getSeats());
			aux.setBulletins(entity.getBulletins());
			aux.setActivities(entity.getActivities());
			aux.setAssigments(entity.getAssigments());
			aux.setGroups(entity.getGroups());
			aux.setTeacher(entity.getTeacher());
			aux.setBibliographiesRecords(entity.getBibliographiesRecords());
			aux.setCategory(entity.getCategory());
			aux.setAdministrator(entity.getAdministrator());
			aux.setStudents(entity.getStudents());
			m = this.repository.save(aux);

			if (LoginService.hasRole("ADMINISTRATOR"))
				this.activityRecordService.RQNcreateReport("edits.subject");

		} else {
			entity.setTicker(this.generateTicker());
			final Administrator admin = this.administratorService.checkPrincipal();
			entity.setAdministrator(admin);
			aux = this.repository.save(entity);

			admin.getSubjects().add(aux);
			this.administratorService.save(admin);

			aux.setAdministrator(admin);
			m = this.repository.save(aux);

			if (LoginService.hasRole("ADMINISTRATOR"))
				this.activityRecordService.RQNcreateReport("creates.subject");
		}
		return m;
	}

	private String generateTicker() {
		final StringBuilder str = new StringBuilder();

		final String letters = new String("QWERTYUIOPASDFGHJKLZXCVBNM");
		final Random rand = new Random();

		for (int i = 0; i < 2; i++)
			str.append(letters.charAt(rand.nextInt(letters.length())));

		str.append('-');

		final String number = new String("0123456789");
		for (int i = 0; i < 5; i++)
			str.append(number.charAt(rand.nextInt(number.length())));

		return str.toString();
	}

	public Subject saveBulletin(final Subject entity) {
		Assert.notNull(entity);

		Subject aux = new Subject();
		Subject saved = null;
		if (this.exists(entity.getId())) {
			if (LoginService.isAnyAuthenticated()) {
				aux = this.repository.findOne(entity.getId());
				aux.setTitle(entity.getTitle());
				aux.setTicker(entity.getTicker());
				aux.setSyllabus(entity.getSyllabus());
				aux.setSeats(entity.getSeats());
				aux.setBulletins(entity.getBulletins());
				aux.setActivities(entity.getActivities());
				aux.setAssigments(entity.getAssigments());
				aux.setGroups(entity.getGroups());
				aux.setTeacher(entity.getTeacher());
				aux.setBibliographiesRecords(entity.getBibliographiesRecords());
				aux.setCategory(entity.getCategory());
				aux.setAdministrator(entity.getAdministrator());
				aux.setStudents(entity.getStudents());

				saved = this.repository.save(aux);
			}

		} else
			saved = this.repository.save(entity);
		return saved;
	}

	public Subject findOne(final Integer id) {
		return this.repository.findOne(id);
	}

	public boolean exists(final Integer id) {
		Assert.notNull(id);
		return this.repository.exists(id);
	}

	public List<Subject> save(final List<Subject> arg0) {
		Assert.notNull(arg0);
		Assert.noNullElements(arg0.toArray());
		return this.repository.save(arg0);
	}

	public void delete(final Subject subject) {
		Assert.notNull(subject);
		Assert.isTrue(LoginService.hasRole("ADMINISTRATOR"));
		subject.setActivities(new ArrayList<Activity>());
		subject.setBulletins(new ArrayList<Bulletin>());

		subject.setGroups(new ArrayList<Group>());
		final List<Student> students = subject.getStudents();
		for (final Student s : students) {
			s.getSubjects().remove(subject);
			this.studentService.save(s);
		}
		subject.setGroups(new ArrayList<Group>());

		subject.setStudents(new ArrayList<Student>());

		subject.setBibliographiesRecords(new ArrayList<BibliographyRecord>());

		subject.setAssigments(new ArrayList<Assignment>());

		final List<School> schools = this.schoolService.findAll();

		for (final School c : schools)
			if (c.getSubjects().contains(subject)) {
				c.getSubjects().remove(subject);
				this.schoolService.save(c);

			}

		final List<Teacher> teachers = this.teacherService.findAll();

		for (final Teacher tea : teachers)
			if (tea.getSubjects().contains(subject)) {
				tea.getSubjects().remove(subject);
				this.teacherService.save(tea);

			}

		final List<Administrator> admins = this.administratorService.findAll();

		for (final Administrator ad : admins)
			if (ad.getSubjects().contains(subject)) {
				ad.getSubjects().remove(subject);
				this.administratorService.save(ad);

			}

		this.repository.delete(subject);
		this.activityRecordService.RQNcreateReport("deletes.subject");

	}

	/**
	 * Devuelve una asignatura que el profesor logueado imparte
	 * 
	 * @param subjectId
	 * @return
	 */

	public Subject findOnePrincipal(final int subjectId) {
		// TODO Auto-generated method stub

		final Subject subject = this.repository.findOne(subjectId);

		return subject;
	}
	/**
	 * Devuelve asignaturas impartidas por el profesor
	 * 
	 * @param checkPrincipal
	 * @return
	 */

	public Collection<Subject> findAllByPrincipal(final Teacher checkPrincipal) {
		// TODO Auto-generated method stub
		Assert.notNull(checkPrincipal);
		return this.repository.findAllByPrincipal(checkPrincipal.getId());
	}

	public Collection<Subject> findSubjectsByWordWithoutSeatsByPrincipal(final String keyword) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		return this.repository.findSubjectsByWordWithoutSeats(keyword, LoginService.getPrincipal().getId());
	}

	public Collection<Subject> findSubjectsByWordWithSeatsByPrincipal(final String keyword) {
		Assert.isTrue(LoginService.isAnyAuthenticated());
		return this.repository.findSubjectsByWordWithSeats(keyword, LoginService.getPrincipal().getId());
	}

	public Subject findOneByAssignment(final int assignmentId) {
		Assert.notNull(assignmentId);

		return this.repository.findOneByAssignment(assignmentId);
	}

	public Subject findOneByGroupId(final int id) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		Assert.isTrue(LoginService.hasRole("STUDENT"));
		final Student student = this.studentService.checkPrincipal();
		final Subject resul = this.repository.findOneByGroupId(id, student.getId());

		return resul;
	}

	public Subject findOnePrincipal2(final Integer id) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		final Subject subject = this.repository.findOne(id);
		final Student student = this.studentService.checkPrincipal();

		Assert.isTrue(student.getSubjects().contains(subject));

		return subject;
	}

}
