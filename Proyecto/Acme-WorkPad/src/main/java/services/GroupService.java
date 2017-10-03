
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GroupRepository;
import security.LoginService;
import domain.Group;
import domain.Student;
import domain.Subject;
import domain.Submission;

@Service
@Transactional
public class GroupService {

	@Autowired
	private GroupRepository	groupRepository;

	@Autowired
	private LoginService			loginService;

	@Autowired
	private StudentService			studentService;

	@Autowired
	private SubjectService			subjectService;


	public GroupService() {
		super();
	}

	public Group create() {
		final Group groupSubject = new Group();
		groupSubject.setName(new String());
		groupSubject.setDescription(new String());
		groupSubject.setStartDate(new Date());
		groupSubject.setEndDate(new Date());
		groupSubject.setSubmission(new ArrayList<Submission>());

		return groupSubject;
	}

	public Group save(final Group entity, final Integer subjectId) {
		Assert.notNull(entity);

		Group aux = new Group();

		final Student a = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (this.exists(entity.getId())) {
			aux = this.groupRepository.findOne(entity.getId());

			aux.setName(entity.getName());
			aux.setDescription(entity.getDescription());
			aux.setStartDate(entity.getStartDate());
			aux.setEndDate(entity.getEndDate());
			aux.setSubmission(entity.getSubmission());
			
			final List<Group> groups = a.getGroups();

			groups.add(aux);
			a.setGroups(groups);

			this.studentService.save(a);
			
			Subject subject = subjectService.findOne(subjectId);
			
			final List<Group> groupsSubject = subject.getGroups();
			groupsSubject.add(aux);
			subject.setGroups(groupsSubject);
			
			this.subjectService.save(subject);
			
			return this.groupRepository.save(aux);

		} else {
			aux = this.groupRepository.save(entity);
			final List<Group> groups = a.getGroups();

			groups.add(aux);
			a.setGroups(groups);

			this.studentService.save(a);
			
			Subject subject = subjectService.findOne(subjectId);
			
			final List<Group> groupsSubject = subject.getGroups();
			groupsSubject.add(aux);
			subject.setGroups(groupsSubject);
			
			this.subjectService.save(subject);
			
			return aux;
		}

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

	public Group findGroupBySubjectAndStudent(int id, int id2) {
		Assert.notNull(id);
		Assert.notNull(id2);
		return this.groupRepository.findGroupBySubjectAndStudent(id,id2);
	}

	public Group save(Group group) {
		return this.groupRepository.save(group);
		
	}

}
