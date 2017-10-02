
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Assignment;
import repositories.AssignmentRepository;

@Component
@Transactional
public class StringToAssignmentConverter implements Converter<String, Assignment> {

	@Autowired
	AssignmentRepository assignmentRepository;


	@Override
	public Assignment convert(final String text) {
		Assignment result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.assignmentRepository.findOne(id);
			}
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
