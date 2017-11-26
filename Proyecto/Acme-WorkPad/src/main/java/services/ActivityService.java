
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import security.LoginService;
import domain.Activity;
import domain.Subject;
import domain.Teacher;
import forms.ActivityForm;

@Transactional
@Service
public class ActivityService {

	@Autowired
	private ActivityRepository		repository;

	@Autowired
	private SubjectService			subjectService;
	@Autowired
	private TeacherService			teacherService;
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActivityRecordService	activityRecordService;


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

		if (LoginService.hasRole("TEACHER") && activity.getId() == 0)
			this.activityRecordService.RQNcreateReport("creates.activity");
		else if (LoginService.hasRole("TEACHER") && activity.getId() != 0)
			this.activityRecordService.RQNcreateReport("edits.activity");

		// Actualizo Asignatura

		subject.getActivities().add(saved);
		this.subjectService.update(subject);

		return saved;
	}

	/**
	 * Actualiza información de una Activity
	 * 
	 * @param activity
	 * @return
	 */

	public Activity update(final Activity activity) {
		Assert.isTrue(this.repository.exists(activity.getId()));
		this.checkIsPrincipal(activity);

		final Activity saved = this.repository.save(activity);
		if (LoginService.hasRole("TEACHER"))
			this.activityRecordService.RQNcreateReport("edits.activity");

		return saved;
	}

	public void delete(final Activity activity) {
		Assert.notNull(activity);
		Assert.isTrue(this.repository.exists(activity.getId()));
		this.checkIsPrincipal(activity);

		final Subject subject = this.subjectService.findSubjectByTeacherIdActivityId(this.teacherService.checkPrincipal().getId(), activity.getId());

		subject.getActivities().remove(activity);
		this.subjectService.update(subject);
		this.repository.delete(activity);
		if (LoginService.hasRole("TEACHER"))
			this.activityRecordService.RQNcreateReport("deletes.activity");
	}

	public void deleteAdmin(final Activity activity) {
		Assert.notNull(activity);
		Assert.isTrue(this.repository.exists(activity.getId()));
		final List<Subject> sub = this.administratorService.checkPrincipal().getSubjects();
		Subject subject = null;
		for (final Subject s : sub)
			if (s.getActivities().contains(activity)) {
				subject = s;
				break;
			}

		subject.getActivities().remove(activity);
		this.subjectService.update(subject);
		this.repository.delete(activity);

	}

	//	Others methods --------------------------------

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

	/**
	 * Comprueba que se tiene permisos sobre la asignatura
	 * 
	 * @param activity
	 */

	private void checkIsPrincipal(final Activity activity) {
		// TODO Auto-generated method stub
		boolean sw = false;

		final Collection<Subject> subjects = this.teacherService.checkPrincipal().getSubjects();
		for (final Subject subject : subjects)
			if (subject.getActivities().contains(activity))
				sw = true;

		Assert.isTrue(sw);
	}

	/**
	 * Devuelve una actividad asignada al profesor logueado en alguna asignatura
	 * 
	 * @param q
	 * @return
	 */

	public Activity findOnePrincipal(final int q) {
		// TODO Auto-generated method stub
		Assert.notNull(q);
		Assert.isTrue(this.repository.exists(q));

		return this.repository.findOneByTeacherIdActivityId(this.teacherService.checkPrincipal().getId(), q);
	}

	public Activity create() {
		// TODO Auto-generated method stub
		final Activity activity = new Activity();

		return activity;
	}

	public void save(final Activity activity) {
		// TODO Auto-generated method stub
		if (activity.getId() != 0)
			this.update(activity);
		else {

		}
	}

	public Activity reconstruct(final ActivityForm activityForm) {
		// TODO Auto-generated method stub
		final Activity activity = new Activity();
		activity.setTitle(activityForm.getTitle());
		activity.setDescription(activityForm.getDescription());
		activity.setStartDate(activityForm.getStartDate());
		activity.setEndDate(activityForm.getEndDate());
		activity.setLink(activityForm.getLink());
		return activity;
	}

	public Collection<Activity> findAllBySubjectPrincipal(final Integer idSubject) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		Assert.isTrue(LoginService.hasRole("TEACHER"));
		final Subject subject = this.subjectService.findOnePrincipal(idSubject);
		Assert.isTrue(this.teacherService.checkPrincipal().getSubjects().contains(subject));

		return subject.getActivities();
	}
}
