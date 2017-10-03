package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
	private SubmissionRepository repository;

	@Autowired
	private StudentService studentService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private GroupService groupService;

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
	private SubmissionRepository submissionRepository;

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

	public SubmissionForm create(int assignmentId) {
		Student student = this.studentService.checkPrincipal();
		Assert.notNull(student);
		Assignment assignment = this.assignmentService.findOne(assignmentId);
		Assert.notNull(assignment);
		Subject subject = this.subjectService.findOneByAssignment(assignmentId);
		Group group = this.groupService.findGroupBySubjectAndStudent(
				subject.getId(), student.getId());
		SubmissionForm form = new SubmissionForm();
		form.setGroupId(group.getId());
		form.setAssignmentId(assignmentId);

		Collection<Submission> submissions = this.submissionRepository
				.findSubmissionsByGroupAndAssignment(assignmentId,
						group.getId());
		if (submissions.isEmpty())
			form.setTryNumber(1);
		else {
			int aux = 0;
			for (Submission s : submissions) {
				if (aux < s.getTryNumber())
					aux = s.getTryNumber();
			}
			form.setTryNumber(aux+1);
		}

		return form;

	}

	// Supporting Services ------------------------------------------
	@Autowired(required = false)
	private Validator validator;

	public Submission reconstruct(SubmissionForm form, BindingResult binding) {
		Submission submission = new Submission();
		String[] array = form.getAttachments().split(",");
		List<String> str = new ArrayList<String>(Arrays.asList(array));

		submission.setAttachments(str);
		submission.setContent(form.getContent());
		submission.setGrade(form.getGrade());
		submission.setTryNumber(form.getTryNumber());
		Group group = this.groupService.findOne(form.getGroupId());
		Assignment assignment = this.assignmentService.findOne(form
				.getAssignmentId());
		this.validator.validate(submission, binding);
		if (!binding.hasErrors()) {
			Submission saved = this.save(submission);
			group.getSubmission().add(saved);
			assignment.getSubmission().add(saved);
			this.groupService.save(group);
			this.assignmentService.save(assignment);
			return saved;
		} else {
			return submission;
		}

	}

}
