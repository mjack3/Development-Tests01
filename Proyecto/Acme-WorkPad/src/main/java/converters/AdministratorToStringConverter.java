package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrator;

@Component
@Transactional
public class AdministratorToStringConverter implements Converter<Administrator, String>{

	@Override
	public String convert(Administrator ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
