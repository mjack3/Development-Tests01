
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Group;
import domain.Subject;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GroupServiceTest extends AbstractTest {

	@Autowired
	private GroupService	groupService;
	@Autowired
	private SubjectService	subjectService;


	//	RQ 11.2

	@Test
	public void driverCreateGroup() {
		final Object testingData1[][] = {
			//  Estudiante crea un grupo en una asignatura
			{
				"student1", 837, InvalidDataAccessApiUsageException.class
			},
			//  Estudiante crea grupo en una asignatura ajena
			{
				"student10", 837, IllegalArgumentException.class
			},
			//  Studiante crea grupo en asignatura ajena
			{
				"student1", 000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.driverCreateGroup((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void driverCreateGroup(final String username, final Integer id, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Group group = this.groupService.create();
			final Subject subject = this.subjectService.findOnePrincipal(id);

			subject.getGroups().add(group);
			this.subjectService.update(subject);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}