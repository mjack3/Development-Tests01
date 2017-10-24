
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRecordRepository;
import security.LoginService;
import domain.ActivityRecord;
import domain.Actor;

@Service
@Transactional
public class ActivityRecordService {

	@Autowired
	private ActivityRecordRepository	activityRecordRepository;
	@Autowired
	private StudentService				studentService;
	@Autowired
	private TeacherService				teacherService;

	@Autowired
	LoginService						loginService;


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
		if (activityRecord.getId() == 0)
			saved = this.activityRecordRepository.save(activityRecord);
		else {
			Assert.isTrue(this.activityRecordRepository.exists(activityRecord.getId()));
			Actor a;
			if (LoginService.hasRole("TEACHER"))
				a = this.teacherService.checkPrincipal();
			else
				a = this.studentService.checkPrincipal();
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
		Actor a;
		if (LoginService.hasRole("TEACHER"))
			a = this.teacherService.checkPrincipal();
		else
			a = this.studentService.checkPrincipal();
		Assert.isTrue(a.getActivitiesRecords().contains(activityRecord));
		this.activityRecordRepository.delete(activityRecord);

	}
}
