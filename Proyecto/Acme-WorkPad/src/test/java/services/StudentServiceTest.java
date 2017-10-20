
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Student;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class StudentServiceTest extends AbstractTest {

	@Autowired
	private TeacherService	teacherService;
	@Autowired
	private StudentService	studentService;
	@Autowired
	private SubjectService	subjectService;


	// RF 11.3

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

	//	RF 10.3

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

	//	RF 10.2

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
			// Estudiante cambia sus datos
			{
				"student1", 715, "nuevoNombre", "nuevos apellidos", null
			},

			// Estudiante edita a otro estudiante
			{
				"student1", 711, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},
			//	No logueado edita a estudiante
			{
				null, 714, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},
			//	No logueado edita a estudiante que no existe
			{
				null, 000, "nuevonombre", "nuevos apellidos", IllegalArgumentException.class
			},

			//	Estudiante pone otro campo diferente vacio
			{
				"student1", 714, "nuevonombre", "", IllegalArgumentException.class
			},
			//	Estudiante pone varios campos vacios
			{
				"student1", 714, "", "", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData1.length; i++)
			this.EditPersonalData((String) testingData1[i][0], (Integer) testingData1[i][1], (String) testingData1[i][2], (String) testingData1[i][3], (Class<?>) testingData1[i][4]);

	}

	private void EditPersonalData(final String username, final Integer idStudent, final String name, final String surname, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Student student = this.studentService.findOne(idStudent);
			student.setName(name);
			student.setSurname(surname);
			this.studentService.editInfo(student);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	//	13.1	----------

	@Test
	public void driverEnrolSubject() {
		final Object testingData1[][] = {
			// Estudiante se apunta a una asignatura
			{
				"student10", 837, null
			},
			// Estudiante se apunta dos veces
			{
				"student1", 837, IllegalArgumentException.class
			},
			// Estudiante se apunta a una asignatura que no existe
			{
				"student10", 000, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.driverEnrolSubject((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void driverEnrolSubject(final String username, final Integer idSubject, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			this.studentService.enrolSubject(idSubject);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
