
package services;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActivityRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.ActivityRecord;
import domain.Actor;

@Service
@Transactional
public class ActivityRecordService {

	@Autowired
	private ActorService				actorService;
	@Autowired
	private ActivityRecordRepository	activityRecordRepository;
	@Autowired
	private StudentService				studentService;
	@Autowired
	private TeacherService				teacherService;

	@Autowired
	LoginService						loginService;

	@Autowired
	private Validator					validator;


	public ActivityRecordService() {
		super();
	}

	public Collection<ActivityRecord> display(final Integer idActor) {
		// TODO Auto-generated method stub
		Assert.notNull(idActor);
		Assert.isTrue(LoginService.isAnyAuthenticated());

		final Collection<ActivityRecord> activityRecords = this.activityRecordRepository.display(idActor);
		return activityRecords;
	}

	public Collection<ActivityRecord> listPrincipal() {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		final Collection<ActivityRecord> activityRecords = this.activityRecordRepository.findAllPrincipal(LoginService.getPrincipal().getId());
		return activityRecords;
	}

	public ActivityRecord save(final ActivityRecord activityRecord) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		ActivityRecord saved = null;

		final Actor a = this.actorService.findOnePrincipal();

		if (activityRecord.getId() == 0) {
			saved = this.activityRecordRepository.save(activityRecord);
			a.getActivitiesRecords().add(saved);
			this.actorService.update(a);
		} else {
			Assert.isTrue(this.activityRecordRepository.exists(activityRecord.getId()));
			if (a.getActivitiesRecords() != null || !a.getActivitiesRecords().isEmpty())
				Assert.isTrue(a.getActivitiesRecords().contains(activityRecord));
			saved = this.activityRecordRepository.save(activityRecord);
		}

		return saved;
	}
	public ActivityRecord findOne(final Integer idARecord) {
		// TODO Auto-generated method stub
		Assert.notNull(idARecord);

		final ActivityRecord activityRecord = this.activityRecordRepository.findOne(idARecord);
		Assert.notNull(activityRecord);
		return activityRecord;
	}

	public void delete(final ActivityRecord activityRecord) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());

		final Actor a = this.actorService.findOnePrincipal();
		Assert.isTrue(a.getActivitiesRecords().contains(activityRecord));

		a.getActivitiesRecords().remove(activityRecord);
		this.actorService.update(a);

		this.activityRecordRepository.delete(activityRecord);

		this.activityRecordRepository.delete(activityRecord);

	}

	public List<ActivityRecord> findAllPrincipal() {
		// TODO Auto-generated method stub

		Assert.isTrue(LoginService.isAnyAuthenticated());
		final UserAccount userAccount = LoginService.getPrincipal();
		final List<ActivityRecord> activityRecords = this.activityRecordRepository.findAllPrincipal(userAccount.getId());
		return activityRecords;
	}

	public ActivityRecord create() {
		// TODO Auto-generated method stub
		return new ActivityRecord();
	}

	public ActivityRecord reconstruct(final ActivityRecord activityRecord, final BindingResult bindingResult) {
		// TODO Auto-generated method stub
		ActivityRecord resul;
		if (activityRecord.getId() == 0)
			resul = this.create();
		else
			resul = this.findOne(activityRecord.getId());

		resul.setDescription(activityRecord.getDescription());
		resul.setAttachments(activityRecord.getAttachments());
		resul.setWrittenDate(activityRecord.getWrittenDate());
		final String pattern = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		for (final String s : resul.getAttachments())
			if (!ActivityRecordService.IsMatch(s, pattern))
				bindingResult.rejectValue("attachments", "URls only", "URLs only");

		this.validator.validate(resul, bindingResult);

		return resul;
	}

	private static boolean IsMatch(final String s, final String pattern) {
		try {
			final Pattern patt = Pattern.compile(pattern);
			final Matcher matcher = patt.matcher(s);
			return matcher.matches();
		} catch (final RuntimeException e) {
			return false;
		}
	}

	public ActivityRecord findOnePrincipal(final int q) {
		// TODO Auto-generated method stub
		Assert.isTrue(LoginService.isAnyAuthenticated());
		final ActivityRecord activityRecord = this.activityRecordRepository.findOnePrincipal(q, LoginService.getPrincipal().getId());
		Assert.notNull(activityRecord);
		return activityRecord;
	}
}
