
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Submission;

@Component
@Transactional
public class SubmissionToStringConverter implements Converter<Submission, String> {

	@Override
	public String convert(Submission ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;

	}

}
