package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Apply;

@Component
@Transactional
public class ApplyToStringConverter implements Converter<Apply, String> {

	@Override
	public String convert(Apply apply) {
		String result;

		if (apply == null){
			result = null;
		}else{
			result = String.valueOf(apply.getId());
		}
		return result;
	}

}
