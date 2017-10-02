
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Bulletin;
import repositories.BulletinRepository;

@Component
@Transactional
public class StringToBulletinConverter implements Converter<String, Bulletin> {

	@Autowired
	BulletinRepository bulletinRepository;


	@Override
	public Bulletin convert(final String text) {
		Bulletin result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.bulletinRepository.findOne(id);
			}
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
