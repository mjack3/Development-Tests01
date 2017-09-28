
package services;

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

}
