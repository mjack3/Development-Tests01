
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Bulletin;

@Component
@Transactional
public class BulletinToStringConverter implements Converter<Bulletin, String> {

	@Override
	public String convert(final Bulletin ar) {
		String res;
		if (ar == null)
			res = null;
		else
			res = String.valueOf(ar.getId());
		return res;
	}

}
