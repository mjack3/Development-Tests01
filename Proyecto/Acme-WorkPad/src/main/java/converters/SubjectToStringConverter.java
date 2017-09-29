
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Subject;

public class SubjectToStringConverter implements Converter<Subject, String> {

	@Override
	public String convert(final Subject actor) {
		String result;

		if (actor == null)
			result = null;
		else
			result = String.valueOf(actor.getId());

		return result;
	}
}
