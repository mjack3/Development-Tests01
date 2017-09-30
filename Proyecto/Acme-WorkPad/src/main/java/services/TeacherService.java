
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.TeacherRepository;
import security.LoginService;
import security.UserAccount;
import domain.Activity;
import domain.Assignment;
import domain.Subject;
import domain.Submission;
import domain.Teacher;
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


	public TeacherService() {
		super();
	}

	//	CRUDs methods --------------------------------

	/**
	 * Actualiza un profesor existente
	 * @param actor Profesor a actualizar
	 * @return profesor actualizado
	 */
	
	public Teacher update(Teacher actor) {
		
		Assert.notNull(actor);
		Assert.isTrue(repository.exists(actor.getId()));
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return repository.save(actor);
	}
	
	/**
	 * Crea un nuevo profesor
	 * @param actor Profesor a crear
	 * @return profesor creado
	 */

	public Teacher create(Teacher actor) {
		
		Assert.notNull(actor);
		Assert.isTrue(actor.getPhone().matches("^$|^\\\\+([1-9][0-9]{0,2}) (\\\\([1-9][0-9]{0,3}\\\\)) ([a-zA-Z0-9 -]{4,})$"));

		return repository.save(actor);
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
	 * A�ade una puntuaci�n a un submission
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
	 * Comprueba que el profesor logueado puede valorar este env�o
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
}
