
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.GroupSubject;
import domain.Student;
import domain.Submission;
import repositories.GroupSubjectRepository;
import security.LoginService;

@Service
@Transactional
public class GroupSubjectService {

	@Autowired
	private GroupSubjectRepository	groupSubjectRepository;

	@Autowired
	private LoginService			loginService;

	@Autowired
	private StudentService			studentService;

	@Autowired
	private SubjectService			subjectService;


	public GroupSubjectService() {
		super();
	}

	public GroupSubject create() {
		final GroupSubject groupSubject = new GroupSubject();
		groupSubject.setName(new String());
		groupSubject.setDescription(new String());
		groupSubject.setStartDate(new Date());
		groupSubject.setEndDate(new Date());
		groupSubject.setSubmission(new ArrayList<Submission>());

		return groupSubject;
	}

	public GroupSubject save(final GroupSubject entity) {
		Assert.notNull(entity);

		GroupSubject aux = new GroupSubject();

		final Student a = (Student) this.loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (this.exists(entity.getId())) {
			aux = this.groupSubjectRepository.findOne(entity.getId());

			aux.setName(entity.getName());
			aux.setDescription(entity.getDescription());
			aux.setStartDate(entity.getStartDate());
			aux.setEndDate(entity.getEndDate());
			aux.setSubmission(entity.getSubmission());
			return this.groupSubjectRepository.save(aux);

		} else {
			aux = this.groupSubjectRepository.save(entity);
			final List<GroupSubject> groups = a.getGroups();

			groups.add(aux);
			a.setGroups(groups);

			this.studentService.save(a);
		}
		return aux;
	}

	public GroupSubject findOne(final Integer id) {
		Assert.notNull(id);
		return this.groupSubjectRepository.findOne(id);
	}

	public boolean exists(final Integer id) {
		Assert.notNull(id);
		return this.groupSubjectRepository.exists(id);
	}

	public List<GroupSubject> findAll() {
		return this.groupSubjectRepository.findAll();
	}

	public void flush() {
		this.groupSubjectRepository.flush();
	}

	public List<GroupSubject> save(final List<GroupSubject> arg0) {
		return this.groupSubjectRepository.save(arg0);
	}

}
