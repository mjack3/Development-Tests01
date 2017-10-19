
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Teacher;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TeacherServiceTest extends AbstractTest {

	@Autowired
	private TeacherService	teacherService;


	@Test
	public void driverListTeacherBySubject() {

		final Object testingData[][] = {
			// profesor accede a los profsores de una asignatira
			{
				"teacher1", 837, null
			},
			//	No logueado accede a los profesores de una asignatura
			{
				null, 837, null
			},
			//	Profesor accede a los profesores de una asignatura que no existe
			{
				"teacher1", 000, IllegalArgumentException.class
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
			//  accede a las asignatura de un profesor
			{
				"teacher1", 710, null
			},
			//  accede a las asignatura de un profesor
			{
				null, 710, null
			},
			//  accede a las asignatura de un profesor que no existe
			{
				"teacher1", 000, IllegalArgumentException.class
			},
			// NoLogin intenta acceder a las asignaturas de un profesor que no existe
			{
				null, 000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.ListSubjectByTeacherTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

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
	//	RF 11.2

	@Test
	public void driverEditPersonalData() {
		final Object testingData1[][] = {
			// teacher1 cambia sus datos
			{
				"teacher1", 710, "nuevoNombre", "nuevos apellidos", null
			},
			//  teacher1 pone a null su nombre
			{
				"teacher1", 711, null, "nuevos apellidos", IllegalArgumentException.class
			},
			// teacher1 edita a otro teacher
			{
				"teacher1", 711, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},
			//	No logueado edita a estudiante
			{
				null, 710, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},
			//	No logueado edita a teacher que no existe
			{
				null, 000, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},
			//	teacher1 pone nombre nulo
			{
				"teacher1", 710, null, "nuevos apellidos", ConstraintViolationException.class
			},
			//	No logueado pone varios campos nulos
			{
				null, 711, null, null, ConstraintViolationException.class
			},
			//	teacher pone otro campo diferente vacio
			{
				"teacher1", 711, "nuevonombre", "", ConstraintViolationException.class
			},
			//	teacher pone varios campos vacios
			{
				"teacher1", 711, "", "", ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData1.length; i++)
			this.EditPersonalData((String) testingData1[i][0], (Integer) testingData1[i][1], (String) testingData1[i][2], (String) testingData1[i][3], (Class<?>) testingData1[i][4]);

	}

	private void EditPersonalData(final String username, final Integer idTeacher, final String name, final String surname, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Teacher teacher = this.teacherService.findOne(idTeacher);
			teacher.setName(name);
			teacher.setSurname(surname);
			this.teacherService.editInfo(teacher);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

}
