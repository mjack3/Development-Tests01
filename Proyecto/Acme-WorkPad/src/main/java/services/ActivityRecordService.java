package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActivityRecordRepository;

@Service
@Transactional
public class ActivityRecordService {
	
	@Autowired
	private ActivityRecordRepository	activityRecordRepository;

	public ActivityRecordService() {
		super();
	}

}
