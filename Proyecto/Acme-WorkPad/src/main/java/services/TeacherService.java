
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
import domain.Subject;
import domain.Teacher;

@Transactional
@Service
public class TeacherService {

	@Autowired
	private TeacherRepository	repository;


	public TeacherService() {
		// TODO Auto-generated constructor stub
	}

	//	CRUDs methods --------------------------------

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

	// Others methods ----------------------------------------

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
