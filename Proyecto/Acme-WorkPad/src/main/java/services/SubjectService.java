
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubjectRepository;
import domain.Subject;

@Transactional
@Service
public class SubjectService {

	@Autowired
	private SubjectRepository	repository;


	public SubjectService() {
		super();
	}

	public Collection<Subject> findSubjectWord(final String word) {
		Assert.notNull(word);
		final Collection<Subject> subjects = this.repository.findSubjectWord(word);

		return subjects;
	}
}
