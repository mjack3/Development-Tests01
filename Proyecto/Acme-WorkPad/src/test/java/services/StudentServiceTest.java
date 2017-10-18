
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class StudentServiceTest extends AbstractTest {

	@Autowired
	private TeacherService	teacherService;


	@Test
	public void driverListTeacherBySubject() {

		Object testingData[][] = {
			// Estudiante accede a los profsores de una asignatira
			{
				"student1", 837, null
			},
			//	No logueado accede a los profesores de una asignatura
			{
				null, 837, null
			},
			//	Estudiante accede a los profesores de una asignatura que no existe
			{
				"student1", 000, IllegalArgumentException.class
			},
			//	No logueado intenta acceder a una asignatura que no existe
			{
				null, 000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.ListTeacherBySubjectTest((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);

		testingData = null;

	}

	@Test
	public void driverListSubjectByTeacher() {
		final Object testingData1[][] = {
			// Estudiante accede a las asignatura de un profesor
			{
				"student1", 710, null
			},
			// Nologin accede a las asignatura de un profesor
			{
				null, 710, null
			},
			// Estudiante accede a las asignatura de un profesor que no existe
			{
				"student1", 000, IllegalArgumentException.class
			},
			//	No logueado intenta acceder a las asignaturas de un profesor que no existe
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

}
