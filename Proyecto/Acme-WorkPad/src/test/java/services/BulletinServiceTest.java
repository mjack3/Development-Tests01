
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Bulletin;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BulletinServiceTest extends AbstractTest {

	@Autowired
	private BulletinService	bulletinService;


	@Test
	public void driverPublishBulletins() {

		Object testingData[][] = {
			// Loged publica bulletin
			{
				"student1", 894, new Bulletin(), JpaSystemException.class
			},
			//	No logueado Loged publica bulletin en una asignatura
			{
				null, 894, new Bulletin(), IllegalArgumentException.class
			},
			//	Profesor publica bulletin en asignatura no válida
			{
				"teacher1", 000, new Bulletin(), JpaSystemException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.PublishBulletinsTest((String) testingData[i][0], (Integer) testingData[i][1], (Bulletin) testingData[i][2], (Class<?>) testingData[i][3]);

		testingData = null;

	}

	protected void PublishBulletinsTest(final String username, final Integer subjectId, Bulletin bulletin, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		bulletin = this.bulletinService.findOne(841);
		bulletin.setId(0);

		caught = null;
		try {

			this.authenticate(username);

			this.bulletinService.saveInSubject2(bulletin, subjectId);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}
}
