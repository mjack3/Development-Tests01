
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Submission;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SubmissionServiceTest extends AbstractTest {

	@Autowired
	private SubmissionService	submissionService;


	//	RQ 11.2

	@Test
	public void driverSubmitDeliverable() {
		final Object testingData1[][] = {
			//  Estudiante Hace entregable en una asigbnatura matricualda
			{
				"student1", 857, null
			},
			//  Estudiante entrega en una asignatura ajena
			{
				"student10", 857, IllegalArgumentException.class
			},
			//  Studiante hace una entrega en una asignatura que no existe
			{
				"student1", 000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.submitDeliverableTest((String) testingData1[i][0], (Integer) testingData1[i][1], (Class<?>) testingData1[i][2]);

	}

	protected void submitDeliverableTest(final String username, final Integer id, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Submission submission = new Submission();
			submission.setContent("sda");
			submission.setAttachments(new ArrayList<String>());
			submission.setGrade(5);
			submission.setMark(55.);
			submission.setTryNumber(2);

			this.submissionService.submissionDerivarable(submission, id);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
