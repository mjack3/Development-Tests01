package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MailMessageRepository;
import domain.MailMessage;

@Component
@Transactional
public class StringToMailMessageConverter implements Converter<String, MailMessage>{
	
	@Autowired
	MailMessageRepository arRepository;
	
	@Override
	public MailMessage convert(String text){
		MailMessage result;
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
