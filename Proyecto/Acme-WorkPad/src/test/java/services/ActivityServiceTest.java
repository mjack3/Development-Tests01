
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Activity;
import domain.Subject;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActivityServiceTest extends AbstractTest {

	@Autowired
	private ActivityService	activityService;
	@Autowired
	private SubjectService	subjectService;


	//	RQ 11.1

	@Test
	public void driverListActivitiesBySubject() {
		final Object testingData1[][] = {
			//  PROFESOR accede a las actividades de una asignatura
			{
				"teacher1", 894, null
			},
			//  NOLOGIN accede a las actividades de una asignatura
			{
				null, 894, IllegalArgumentException.class
			},
			//  PROFESOR accede a la actividades de una asignatura ajena
			{
				"teacher2", 894, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.driverListActivitiesBySubject((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void driverListActivitiesBySubject(final String username, final Integer idSubject, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			this.activityService.findAllBySubjectPrincipal(idSubject);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditActivitiesBySubject() {
		final Object testingData1[][] = {
			//  PROFESOR accede a las actividades de una asignatura
			{
				"teacher1", 850, null
			},
			//  NOLOGIN accede a las actividades de una asignatura
			{
				null, 850, IllegalArgumentException.class
			},
			//  PROFESOR accede a la actividades de una asignatura ajena
			{
				"teacher2", 850, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.driverEditActivitiesBySubject((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void driverEditActivitiesBySubject(final String username, final Integer id, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Activity activity = this.activityService.findOnePrincipal(id);
			this.activityService.update(activity);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateActivitiesBySubject() {
		final Object testingData1[][] = {
			//  PROFESOR Crea actividad en una asignatura
			{
				"teacher1", 850, 894, IllegalArgumentException.class
			},
			//  NOLOGIN dita actividades de una asignatura
			{
				null, 850, 894, JpaSystemException.class
			},
			//  PROFESOR crea actividad en una asigbnatura ajena 
			{
				"teacher2", 850, 894, JpaSystemException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.driverCreateActivitiesBySubject((String) testingData1[i][0], (Integer) testingData1[i][1], (Integer) testingData1[i][2], (Class<?>) testingData1[i][3]);

	}

	protected void driverCreateActivitiesBySubject(final String username, final Integer idActi, final Integer idAsig, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Activity activity = this.activityService.findOnePrincipal(idActi);
			activity.setId(0);
			activity.setVersion(0);
			activity.setTitle("dsada");

			final Subject subject = this.subjectService.findOnePrincipal(idAsig);
			this.activityService.save(activity, subject);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
