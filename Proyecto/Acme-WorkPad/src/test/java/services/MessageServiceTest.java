
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MailMessage;
import domain.Priority;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MailMessageService		mailMessageService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private StudentService			studentService;


	@Test
	public void driverExchangeMessages() {

		final Object testingData[][] = {
			// Admin Intercambia mensajes
			{
				"admin", 715, null
			},
			//	No logueado intercambia mensajes
			{
				null, 715, IllegalArgumentException.class
			},
			//	Usuario intercambia mensaje con alguien no existente
			{
				"admin", 000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.ExchangeMessages((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void ExchangeMessages(final String username, final Integer idDestinty, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final MailMessage message = new MailMessage();
			message.setBody("odaso");
			message.setSubject("asd");
			final Priority p = new Priority();
			p.setValue("NEUTRAL");
			message.setPriority(p);
			message.setSender(this.administratorService.checkPrincipal());
			message.setRecipient(this.studentService.findOne(idDestinty));
			message.setSent(new Date(System.currentTimeMillis() - 1));
			this.mailMessageService.save(message);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}
}
