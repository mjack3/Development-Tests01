
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubmissionRepository;
import domain.Assignment;
import domain.Group;
import domain.Student;
import domain.Subject;
import domain.Submission;
import forms.SubmissionForm;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	repository;

	@Autowired
	private StudentService			studentService;
	@Autowired
	private AssignmentService		assignmentService;
	@Autowired
	private SubjectService			subjectService;
	@Autowired
	private GroupService			groupService;
	@Autowired
	private TeacherService			teacherService;


	public SubmissionService() {
		super();
	}

	public Submission save(final Submission submission) {
		// TODO Auto-generated method stub
		Assert.notNull(submission);
		final Submission saved = this.repository.save(submission);
		return saved;
	}


	/*
	 * KARLI
	 */

	@Autowired
	private SubmissionRepository	submissionRepository;


	public boolean exists(final Integer id) {
		return this.submissionRepository.exists(id);
	}

	public List<Submission> findAll() {
		return this.submissionRepository.findAll();
	}

	public Submission findOne(final Integer id) {
		return this.submissionRepository.findOne(id);
	}

	public <S extends Submission> List<S> save(final Iterable<S> arg0) {
		return this.submissionRepository.save(arg0);
	}

	public SubmissionForm create(final int assignmentId) {
		final Student student = this.studentService.checkPrincipal();
		Assert.notNull(student);
		final Assignment assignment = this.assignmentService.findOne(assignmentId);
		Assert.notNull(assignment);
		final Subject subject = this.subjectService.findOneByAssignment(assignmentId);
		final Group group = this.groupService.findGroupBySubjectAndStudent(subject.getId(), student.getId());
		final SubmissionForm form = new SubmissionForm();
		form.setGroupId(group.getId());
		form.setAssignmentId(assignmentId);

		final Collection<Submission> submissions = this.submissionRepository.findSubmissionsByGroupAndAssignment(assignmentId, group.getId());
		if (submissions.isEmpty())
			form.setTryNumber(1);
		else {
			int aux = 0;
			for (final Submission s : submissions)
				if (aux < s.getTryNumber())
					aux = s.getTryNumber();
			form.setTryNumber(aux + 1);
		}

		return form;

	}


	// Supporting Services ------------------------------------------
	@Autowired(required = false)
	private Validator	validator;


	public Submission reconstruct(final SubmissionForm form, final BindingResult binding) {
		final Submission submission = new Submission();
		final String[] array = form.getAttachments().split(",");
		final List<String> str = new ArrayList<String>(Arrays.asList(array));

		for (final String s : str) {
			final boolean b = Pattern
				.matches(
					"(?i)\\b(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?\\b",
					s.trim());
			Assert.isTrue(b, "error.attachment.format");
		}

		submission.setAttachments(str);
		submission.setContent(form.getContent());
		submission.setGrade(form.getGrade());
		submission.setTryNumber(form.getTryNumber());
		final Group group = this.groupService.findOne(form.getGroupId());
		final Assignment assignment = this.assignmentService.findOne(form.getAssignmentId());
		this.validator.validate(submission, binding);
		if (!binding.hasErrors()) {
			final Submission saved = this.save(submission);
			group.getSubmission().add(saved);
			assignment.getSubmission().add(saved);
			this.groupService.save(group);
			this.assignmentService.save(assignment);
			return saved;
		} else
			return submission;

	}

	/**
	 * Devuelve un submission del profesor logueado
	 * 
	 * @param submissionId
	 * @return
	 */
	public Submission findOnePrincipal(final int submissionId) {
		// TODO Auto-generated method stub
		Assert.notNull(submissionId);
		return this.repository.findOneTeacherIdSubmissionId(this.teacherService.checkPrincipal().getId(), submissionId);
	}

	/**
	 * funciona solo para valorar
	 * 
	 * @param submission
	 * @param bindingResult
	 * @return
	 */

	public Submission reconstruct(final Submission submission, final BindingResult bindingResult) {
		// TODO Auto-generated method stub
		final Submission resul = this.findOnePrincipal(submission.getId());

		resul.setMark(submission.getGrade() * 0.1);
		resul.setGrade(submission.getGrade());
		this.validator.validate(resul, bindingResult);
		return resul;
	}

	public void submissionDerivarable(final Submission submission, final Integer id) {
		// TODO Auto-generated method stub

		final Assignment assignment = this.assignmentService.findOne(id);
		Assert.notNull(assignment);
		final Collection<Assignment> assignments = this.assignmentService.findAllByPrincipalStudent();
		Assert.isTrue(assignments.contains(assignment));
		assignment.getSubmission().add(submission);
		this.assignmentService.save(assignment);

	}

}
