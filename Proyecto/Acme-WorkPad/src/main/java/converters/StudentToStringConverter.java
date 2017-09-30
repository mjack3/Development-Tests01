package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Student;

@Component
@Transactional
public class StudentToStringConverter implements Converter<Student, String>{

	@Override
	public String convert(Student ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}
}
