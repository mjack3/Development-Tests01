
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SeminarRepository;
import security.LoginService;
import domain.Seminar;
import domain.Student;
import domain.Teacher;

@Transactional
@Service
public class SeminarService {

	@Autowired
	private SeminarRepository	repository;

	@Autowired
	private TeacherService		teacherService;
	@Autowired
	private StudentService		studentService;


	public SeminarService() {
		super();
	}

	public void delete(final Seminar entity) {
		Assert.notNull(entity);
		Assert.isTrue(LoginService.hasRole("TEACHER"));
		Assert.isTrue(this.repository.exists(entity.getId()));
		this.repository.delete(entity);
	}

	public Seminar create() {
		final Seminar seminar = new Seminar();

		seminar.setDuration(0);
		seminar.setHall("");

		seminar.setSeats(0);
		seminar.setSummary("");
		seminar.setTitle("");

		return seminar;
	}

	public Seminar save(final Seminar entity) {

		return this.repository.save(entity);
	}

	public Seminar update(final Seminar entity) {
		Assert.notNull(entity);
		Assert.notNull(entity.getId());
		Assert.isTrue(this.repository.exists(entity.getId()));

		return this.repository.save(entity);
	}

	public List<Seminar> findAll() {
		return this.repository.findAll();
	}

	public Seminar findOne(final Integer id) {
		Assert.notNull(id);
		final Seminar seminar = this.repository.findOne(id);
		Assert.notNull(seminar);
		return seminar;
	}

	public Collection<Seminar> listAllPrincipal() {
		// TODO Auto-generated method stub
		final Teacher teacher = this.teacherService.checkPrincipal();
		final Collection<Seminar> seminars = this.repository.findAllIdTeacher(teacher.getId());

		return seminars;
	}

	public void create(final Seminar entity) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		Assert.isTrue(LoginService.hasRole("TEACHER"));

		final Seminar seminar = this.save(entity);
	}

	public void register(final Seminar seminar) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.hasRole("STUDENT"));
		final Student student = this.studentService.checkPrincipal();
		Assert.isTrue(!student.getSeminars().contains(seminar));
		student.getSeminars().add(seminar);
		this.studentService.save(student);
	}

	public void unRegister(final Seminar seminar) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.hasRole("STUDENT"));
		final Student student = this.studentService.checkPrincipal();
		Assert.isTrue(student.getSeminars().contains(seminar));
		student.getSeminars().remove(seminar);
		this.studentService.save(student);
	}

}
