
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AssignmentRepository;
import domain.Assignment;
import domain.Student;
import domain.Subject;
import domain.Submission;
import forms.AssignmentForm;

@Service
@Transactional
public class AssignmentService {

	@Autowired
	private AssignmentRepository	repository;
	@Autowired
	private TeacherService			teacherService;
	@Autowired
	private SubjectService			subjectService;
	@Autowired
	private StudentService			studentService;
	@Autowired
	private GroupService			groupService;


	public AssignmentService() {
		super();
	}

	// Simple CRUDs methods ------------------------------

	/**
	 * Guarda en la BD un assignment
	 * 
	 * @param assignment
	 * @return Assignement
	 */

	public Assignment save(final Assignment assignment) {
		Assert.notNull(assignment);
		final Assignment saved = this.repository.save(assignment);

		return saved;
	}
	public Assignment update(final Assignment assignment) {
		// TODO Auto-generated method stub
		Assert.isTrue(this.repository.exists(assignment.getId()));
		final Assignment saved = this.repository.save(assignment);
		return saved;
	}

	public void delete(final Assignment assignment) {
		// TODO Auto-generated method stub
		Assert.notNull(assignment);
		Assert.isTrue(this.repository.exists(assignment.getId()));
		this.teacherService.checkPrincipal();
		this.repository.delete(assignment);
	}

	public Assignment findOne(final int assignmentId) {
		Assert.notNull(assignmentId);
		return this.repository.findOne(assignmentId);
	}

	/**
	 * Devuelve todas las assigments del profesor logueado por el id de una asignatura
	 * 
	 * @param subjectId
	 * @return
	 */
	public Collection<Assignment> findAllPrincipalBySubjectId(final int subjectId) {
		// TODO Auto-generated method stub

		return this.repository.findAllTeacherIdSubjectId(this.teacherService.checkPrincipal().getId(), subjectId);
	}

	public Assignment reconstruct(final AssignmentForm assignmentForm) {
		// TODO Auto-generated method stub
		Assignment assignment;

		if (assignmentForm.getId() == 0) {
			assignment = new Assignment();
			final List<Submission> list = new ArrayList<Submission>();
			assignment.setSubmission(list);
		} else
			assignment = this.findOne(assignmentForm.getId());

		assignment.setDescription(assignmentForm.getDescription());
		assignment.setEndDate(assignmentForm.getEndDate());
		assignment.setLink(assignmentForm.getLink());
		assignment.setStartDate(assignmentForm.getStartDate());
		assignment.setTitle(assignmentForm.getTitle());

		return assignment;
	}

	/**
	 * Devuelvge un assignmenet del profesor logueado
	 * 
	 * @param q
	 * @return
	 */
	public Assignment findOnePrinicpal(final int q) {
		// TODO Auto-generated method stub
		return this.repository.findOneTeacherIdAssignmentId(this.teacherService.checkPrincipal().getId(), q);
	}

	public Assignment save(final Assignment assignment, final Subject subject) {
		// TODO Auto-generated method stub
		Assignment saved;
		if (assignment.getId() == 0) {
			saved = this.repository.save(assignment);
			subject.getAssigments().add(saved);
			this.subjectService.save(subject);
		} else
			saved = this.repository.save(assignment);

		return saved;
	}

	public Collection<Assignment> findAllByPrincipalStudent() {
		final Student student = this.studentService.checkPrincipal();
		Assert.notNull(student);

		final Collection<Assignment> resul = this.repository.findAllByPrincipalStudent(student.getId());
		return resul;
	}

	public Collection<Submission> findAllByGroupId(final int id) {
		Assert.notNull(id);
		Assert.isTrue(this.groupService.exists(id));
		return this.repository.findAllByGroupId(id);
	}

}
