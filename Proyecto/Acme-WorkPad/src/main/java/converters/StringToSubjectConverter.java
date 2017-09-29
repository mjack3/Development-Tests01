
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.SubjectRepository;
import domain.Subject;

@Component
@Transactional
public class StringToSubjectConverter implements Converter<String, Subject> {

	@Autowired
	SubjectRepository	actorRepository;


	@Override
	public Subject convert(final String text) {
		Subject result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.actorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
