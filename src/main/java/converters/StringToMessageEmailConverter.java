
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MessageEmailRepository;
import domain.MessageEmail;

@Component
@Transactional
public class StringToMessageEmailConverter implements Converter<String, MessageEmail> {

	@Autowired
	MessageEmailRepository	messageRepository;


	@Override
	public MessageEmail convert(final String text) {
		MessageEmail result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.messageRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
