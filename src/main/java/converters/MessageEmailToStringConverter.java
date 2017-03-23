package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MessageEmail;

@Component
@Transactional
public class MessageEmailToStringConverter implements Converter<MessageEmail, String> {

	@Override
	public String convert(MessageEmail message) {
		String result;

		if (message == null){
			result = null;
		}else{
			result = String.valueOf(message.getId());
		}
		return result;
	}

}
