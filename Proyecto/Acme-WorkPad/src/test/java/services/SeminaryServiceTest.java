
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Seminar;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SeminaryServiceTest extends AbstractTest {

	@Autowired
	private SeminarService	seminarService;


	@Test
	public void driverListSeminaries() {
		final Object testingData1[][] = {
			//  PROFESOR accede a las actividades de una asignatura
			{
				"teacher1", null
			},
			//  NOLOGIN accede a las actividades
			{
				null, IllegalArgumentException.class
			},
			//  PROFESOR accede a la actividades nulas
			{
				"student1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.ListSeminariesTest((String) testingData1[i][0], (Class<?>) testingData1[i][1]);

	}

	protected void ListSeminariesTest(final String username, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			this.seminarService.listAllPrincipal();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditSeminars() {
		final Object testingData1[][] = {
			//  profesor edita y crea seminario
			{
				"teacher1", 827, null
			},
			//  NOLOGIN accede a las actividades
			{
				null, 827, IllegalArgumentException.class
			}, {
				"student1", 827, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.editSeminarsTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void editSeminarsTest(final String username, final Integer idActor, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Seminar seminar = new Seminar();
			seminar.setDuration(3);
			seminar.setHall("fas");
			seminar.setOrganisedDate(new Date());
			seminar.setSeats(5);
			seminar.setSummary("adsas");
			seminar.setTitle("asdasd");

			this.seminarService.create(seminar);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteSeminars() {
		final Object testingData1[][] = {
			//  profesor edita y crea seminario
			{
				"teacher1", 827, null
			},
			//  NOLOGIN accede a las actividades
			{
				null, 827, IllegalArgumentException.class
			}, {
				"student1", 827, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.DeleteSeminarsTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void DeleteSeminarsTest(final String username, final Integer idSeminar, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Seminar seminar = this.seminarService.findOne(idSeminar);

			this.seminarService.delete(seminar);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverRegisterSeminars() {
		final Object testingData1[][] = {
			//  profesor edita y crea seminario
			{
				"student10", 827, null
			},
			//  NOLOGIN accede a las actividades
			{
				null, 827, IllegalArgumentException.class
			}, {
				"teacher", 827, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.registerSeminarsTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void registerSeminarsTest(final String username, final Integer idSeminar, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Seminar seminar = this.seminarService.findOne(idSeminar);

			this.seminarService.register(seminar);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverUnRegisterSeminars() {
		final Object testingData1[][] = {
			//  profesor edita y crea seminario
			{
				"student1", 827, null
			},
			//  NOLOGIN accede a las actividades
			{
				null, 827, IllegalArgumentException.class
			}, {
				"teacher", 827, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.unRegisterSeminarsTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void unRegisterSeminarsTest(final String username, final Integer idSeminar, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Seminar seminar = this.seminarService.findOne(idSeminar);

			this.seminarService.unRegister(seminar);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
