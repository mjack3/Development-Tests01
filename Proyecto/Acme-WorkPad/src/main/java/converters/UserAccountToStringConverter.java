package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import security.UserAccount;

@Component
@Transactional
public class UserAccountToStringConverter implements Converter<UserAccount, String>{

	@Override
	public String convert(UserAccount ar) {
		String res;
		if (ar == null) {
			res = null;
		} else {
			res = String.valueOf(ar.getId());
		}
		return res;
	}

}
