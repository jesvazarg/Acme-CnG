
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ApplyRepository;
import domain.Apply;

@Component
@Transactional
public class StringToApplyConverter implements Converter<String, Apply> {

	@Autowired
	ApplyRepository	applyRepository;


	@Override
	public Apply convert(final String text) {
		Apply result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.applyRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
