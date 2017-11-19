
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.GroupRepository;
import security.LoginService;
import domain.Group;
import domain.Student;
import domain.Subject;
import forms.GroupForm;

@Service
@Transactional
public class GroupService {

	@Autowired
	private GroupRepository	groupRepository;

	@Autowired
	private LoginService	loginService;

	@Autowired
	private StudentService	studentService;

	@Autowired
	private SubjectService	subjectService;


	public GroupService() {
		super();
	}

	public void delete(final Group entity) {
		Assert.notNull(entity);
		this.groupRepository.delete(entity);
	}

	public Group create() {
		final Group groupSubject = new Group();

		Assert.isTrue(LoginService.isAnyAuthenticated());
		Assert.isTrue(LoginService.hasRole("STUDENT"));

		final Student student = this.studentService.checkPrincipal();
		groupSubject.getStudents().add(student);

		return groupSubject;
	}

	public Group save(final Group entity, final Integer subjectId) {
		Assert.notNull(entity);

		Group aux = new Group();

		final Student a = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (this.exists(entity.getId())) {
			aux = this.groupRepository.findOne(entity.getId());

			aux = this.groupRepository.saveAndFlush(entity);

		} else {

			aux = this.groupRepository.save(entity);
			final Student student = this.studentService.checkPrincipal();

			student.getGroups().add(aux);
			this.studentService.update(student);
			final Subject subject = this.subjectService.findOne(subjectId);
			subject.getGroups().add(aux);
			this.subjectService.update(subject);

		}

		return aux;
	}

	public Group findOne(final Integer id) {
		Assert.notNull(id);
		return this.groupRepository.findOne(id);
	}

	public boolean exists(final Integer id) {
		Assert.notNull(id);
		return this.groupRepository.exists(id);
	}

	public List<Group> findAll() {
		return this.groupRepository.findAll();
	}

	public void flush() {
		this.groupRepository.flush();
	}

	public List<Group> save(final List<Group> arg0) {
		return this.groupRepository.save(arg0);
	}

	public Group findGroupBySubjectAndStudent(final int id, final int id2) {
		Assert.notNull(id);
		Assert.notNull(id2);
		return this.groupRepository.findGroupBySubjectAndStudent(id, id2);
	}

	public Group save(final Group group) {
		return this.groupRepository.save(group);

	}

	public List<Group> studentByGroups(final int q) {
		return this.groupRepository.studentByGroups(q);
	}

	public void joinGroup(final Integer idGroup) {
		// TODO Auto-generated method stub
		Assert.notNull(idGroup);
		final Student student = this.studentService.checkPrincipal();
		final Group group = this.groupRepository.findOneNoJoinPrincipal(idGroup, student.getId());
		Assert.notNull(group);
		Assert.isTrue(!student.getGroups().contains(group));

		student.getGroups().add(group);
		this.studentService.update(student);
	}


	@Autowired
	private Validator	validator;


	public Group reconstruct(final GroupForm groupForm, final BindingResult binding) {
		// TODO Auto-generated method stub
		final Group resul = this.create();

		resul.setName(groupForm.getName());
		resul.setDescription(groupForm.getDescription());
		resul.setEndDate(groupForm.getEndDate());
		resul.setStartDate(groupForm.getStartDate());

		this.validator.validate(groupForm, binding);

		return resul;
	}

}
