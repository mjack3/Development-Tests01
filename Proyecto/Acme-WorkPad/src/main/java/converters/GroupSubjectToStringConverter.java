
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.GroupSubject;

@Component
@Transactional
public class GroupSubjectToStringConverter implements Converter<GroupSubject, String> {

	@Override
	public String convert(GroupSubject ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
