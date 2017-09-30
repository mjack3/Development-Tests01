package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Teacher;

@Component
@Transactional
public class TeacherToStringConverter implements Converter<Teacher, String>{

	@Override
	public String convert(Teacher ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}
}
