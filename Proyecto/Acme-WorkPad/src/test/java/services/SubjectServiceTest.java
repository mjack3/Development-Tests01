
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
public class SubjectServiceTest extends AbstractTest {

	@Autowired
	private SubjectService	subjectService;


	@Test
	public void driverSearchSubjectskeyWord() {

		final Object testingData[][] = {
			// Student realiza busqueda con asientos
			{
				"student1", "", true, null
			},
			//	No logueado realiza busqueda con asientos
			{
				null, "", true, null
			},
			//	Student realiza busqueda sin asientos
			{
				"student1", "", false, null
			},
			//	No logueado realiza busqueda sin asientos
			{
				null, "", false, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.SearchSubjectskeyWord((String) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// RF 10.4 Y 11.1
	protected void SearchSubjectskeyWord(final String username, final String keyWord, final Boolean sw, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			if (sw)
				this.subjectService.findSubjectsByWordWithoutSeats(keyWord);
			else
				this.subjectService.findSubjectsByWordWithSeats(keyWord);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}
}
