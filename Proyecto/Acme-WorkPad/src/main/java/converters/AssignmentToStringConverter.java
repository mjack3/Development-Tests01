
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Assignment;

@Component
@Transactional
public class AssignmentToStringConverter implements Converter<Assignment, String> {

	@Override
	public String convert(final Assignment ar) {
		String res;
		if (ar == null)
			res = null;
		else
			res = String.valueOf(ar.getId());
		return res;
	}

}
