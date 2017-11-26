
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private TeacherService			teacherService;

	@Autowired
	SubjectService					subjectService;
	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void driverListTeacherBySubject() {

		final Object testingData[][] = {
			// Admin accede a los profsores de una asignatira
			{
				"admin", 894, null
			},
			//	No logueado accede a los profesores de una asignatura
			{
				null, 894, null
			},
			//	Admin accede a los profesores de una asignatura que no existe
			{
				"admin", 000, IllegalArgumentException.class
			},
			//	No logueado intenta acceder a una asignatura que no existe
			{
				null, 000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.ListTeacherBySubjectTest((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverListSubjectByTeacher() {
		final Object testingData1[][] = {
			// Admin accede a las asignatura de un profesor
			{
				"admin", 765, null
			},
			// NoLogin accede a las asignatura de un profesor
			{
				null, 765, null
			},
			// Admin accede a las asignatura de un profesor que no existe
			{
				"admin", 000, IllegalArgumentException.class
			},
			// NoLogin intenta acceder a las asignaturas de un profesor que no existe
			{
				null, 000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.ListSubjectByTeacherTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void ListTeacherBySubjectTest(final String username, final Integer idSubject, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			this.teacherService.listTeacherBySubject(idSubject);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void ListSubjectByTeacherTest(final String username, final Integer idTeacher, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			this.teacherService.listSubjectsByTeacher(idTeacher);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//	RF 11.2

	@Test
	public void driverEditPersonalData() {
		final Object testingData1[][] = {
			// Admin cambia sus datos
			{
				"admin", 760, "nuevoNombre", "nuevos apellidos", null
			},

			// Admin edita a otro actor 
			{
				"admin", 765, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},
			//	No logueado edita a admin
			{
				null, 765, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},
			//	No logueado edita a admin que no existe
			{
				null, 000, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.EditPersonalData((String) testingData1[i][0], (Integer) testingData1[i][1], (String) testingData1[i][2], (String) testingData1[i][3], (Class<?>) testingData1[i][4]);

	}

	private void EditPersonalData(final String username, final Integer idAdmin, final String name, final String surname, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Administrator admin = this.administratorService.findOne(idAdmin);
			admin.setName(name);
			admin.setSurname(surname);
			this.administratorService.editInfo(admin);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

}
