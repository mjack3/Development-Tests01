
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

	public Collection<Subject> findSubjectsByWordWithoutSeats(final String word) {
		Assert.notNull(word);
		final Collection<Subject> subjects = this.repository.findSubjectsByWordWithoutSeats(word);

		return subjects;
	}

	public Collection<Subject> findSubjectsByWordWithSeats(final String word) {
		Assert.notNull(word);
		final Collection<Subject> subjects = this.repository.findSubjectsByWordWithSeats(word);

		return subjects;
	}

	/**
	 * Actualiza la información de una asignatura
	 * 
	 * @param subject
	 */

	public void update(final Subject subject) {
		// TODO Auto-generated method stub
		Assert.isTrue(this.repository.exists(subject.getId()));
		this.repository.save(subject);
	}

	/**
	 * Devuelve la asignatura asociada a un profesor y a una actividad
	 * @param id
	 * @param id2
	 * @return
	 */
	
	public Subject findSubjectByTeacherIdActivityId(int id, int id2) {
		// TODO Auto-generated method stub
		Assert.notNull(id);
		Assert.notNull(id2);
		
		Subject subject = repository.findSubjectByTeacherIdActivityId(id, id2);
		Assert.notNull(subject);
		return subject;
	}

}
