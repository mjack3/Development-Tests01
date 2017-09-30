
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubjectRepository;
import security.LoginService;
import domain.Student;
import domain.Subject;

@Transactional
@Service
public class SubjectService {

	@Autowired
	private SubjectRepository	repository;

	@Autowired
	private StudentService		studentService;

	@Autowired
	private LoginService		loginService;


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
		final Student a = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (this.exists(entity.getId())) {

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

			return this.repository.save(aux);

		} else {
			aux = this.repository.save(entity);
			final List<Subject> subjects = a.getSubjects();

			subjects.add(aux);
			a.setSubjects(subjects);
			this.studentService.save(a);

		}
		return this.repository.save(entity);
	}

	public Subject findOne(final Integer id) {
		return this.repository.findOne(id);
	}

	public boolean exists(final Integer id) {
		Assert.notNull(id);
		return this.repository.exists(id);
	}

	public List<Subject> save(final List<Subject> arg0) {
		return this.repository.save(arg0);
	}

}
