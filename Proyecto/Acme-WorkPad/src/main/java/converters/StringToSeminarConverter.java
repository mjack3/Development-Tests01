package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Seminar;
import repositories.SeminarRepository;

@Component
@Transactional
public class StringToSeminarConverter implements Converter<String,Seminar>{

	@Autowired
	SeminarRepository arRepository;


	@Override
	public Seminar convert(String text) {
		Seminar result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = arRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
