package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BibliographyRecord;

@Component
@Transactional
public class BibliographyRecordToStringConverter implements Converter<BibliographyRecord, String> {

	@Override
	public String convert(BibliographyRecord ar) {
		String result;

		if (ar == null)
			result = null;
		else
			result = String.valueOf(ar.getId());

		return result;
	}
}
