
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.ActivityRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActivityRecordTest extends AbstractTest {

	@Autowired
	private ActivityRecordService	activityRecordService;


	@Test
	public void driverDisplayActivityRecord() {
		final Object testingData1[][] = {
			//  PROFESOR accede a las actividades de una asignatura
			{
				"student1", 715, null
			},
			//  NOLOGIN accede a las actividades
			{
				null, 725, IllegalArgumentException.class
			},
			//  PROFESOR accede a la actividades nulas
			{
				"teacher1", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.DisplayActivityRecordTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void DisplayActivityRecordTest(final String username, final Integer idActor, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			this.activityRecordService.display(idActor);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverListActivityRecord() {
		final Object testingData1[][] = {
			//  PROFESOR accede a las actividades de una asignatura
			{
				"student1", null
			},
			//  NOLOGIN accede a las actividades
			{
				null, IllegalArgumentException.class
			},
			//  PROFESOR accede a la actividades 
			{
				"teacher1", null
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.ListActivityRecordTest((String) testingData1[i][0], (Class<?>) testingData1[i][1]);

	}

	protected void ListActivityRecordTest(final String username, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			this.activityRecordService.listPrincipal();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditActivityRecord() {
		final Object testingData1[][] = {
			//  PROFESOR accede a las actividades de una asignatura
			{
				"student1", 875, null
			},
			//  NOLOGIN accede a las actividades
			{
				null, 875, IllegalArgumentException.class
			},
			//  PROFESOR accede a la actividades 
			{
				"student10", 875, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.EditActivityRecordTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void EditActivityRecordTest(final String username, final Integer idARecord, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final ActivityRecord activityRecord = this.activityRecordService.findOne(idARecord);
			this.activityRecordService.save(activityRecord);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteActivityRecord() {
		final Object testingData1[][] = {
			//  PROFESOR accede a las actividades de una asignatura
			{
				"student1", 875, null
			},
			//  NOLOGIN accede a las actividades
			{
				null, 873, IllegalArgumentException.class
			},
			//  PROFESOR accede a la actividades 
			{
				"student10", 875, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.driverDeleteActivityRecord((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void driverDeleteActivityRecord(final String username, final Integer idARecord, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final ActivityRecord activityRecord = this.activityRecordService.findOne(idARecord);
			this.activityRecordService.delete(activityRecord);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
