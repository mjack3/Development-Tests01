
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.School;

@Component
@Transactional
public class SchoolToStringConverter implements Converter<School, String> {

	@Override
	public String convert(final School ar) {
		String res;
		if (ar == null)
			res = null;
		else
			res = String.valueOf(ar.getId());
		return res;
	}

}
