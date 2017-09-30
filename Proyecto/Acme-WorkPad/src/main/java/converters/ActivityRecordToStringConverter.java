package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ActivityRecord;

@Component
@Transactional
public class ActivityRecordToStringConverter implements Converter<ActivityRecord, String>{

	@Override
	public String convert(ActivityRecord ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
