
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Subject;
import domain.Teacher;

@Transactional
@Service
public class ActivityService {

	@Autowired
	private ActivityRepository	repository;

	@Autowired
	private SubjectService		subjectService;
	@Autowired
	private TeacherService		teacherService;


	public ActivityService() {
		super();
	}

	// CRUDs methods ----------------------------------

	/**
	 * Registra una nueva actividad en una asignatura
	 * 
	 * @param activity
	 * @param subject
	 * @return actividad guardada
	 */

	public Activity save(final Activity activity, final Subject subject) {
		Assert.notNull(activity);
		Assert.notNull(subject);
		this.checkNotContainsActivity(subject, activity);
		this.checkPrincipal(subject);

		// Guardo actividad

		final Activity saved = this.repository.save(activity);

		// Actualizo Asignatura

		subject.getActivities().add(saved);
		this.subjectService.update(subject);

		return saved;
	}

	/**
	 * Comprueba que el profesor es instructor en la asignatura
	 * 
	 * @param subject
	 */

	private void checkPrincipal(final Subject subject) {
		// TODO Auto-generated method stub
		final Teacher teacher = this.teacherService.checkPrincipal();
		Assert.isTrue(teacher.getSubjects().contains(subject));
	}

	/**
	 * Comprueba que la actividad no está en la asignatura
	 * 
	 * @param subject
	 * @param activity
	 */

	private void checkNotContainsActivity(final Subject subject, final Activity activity) {
		// TODO Auto-generated method stub
		Assert.isTrue(!subject.getActivities().contains(activity));
	}
}
