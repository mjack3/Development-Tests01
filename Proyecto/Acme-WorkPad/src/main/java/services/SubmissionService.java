
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	repository;


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

}
