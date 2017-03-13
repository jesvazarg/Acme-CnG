package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Transaction;

@Component
@Transactional
public class TransactionToStringConverter implements Converter<Transaction, String> {

	@Override
	public String convert(Transaction transaction) {
		String result;

		if (transaction == null){
			result = null;
		}else{
			result = String.valueOf(transaction.getId());
		}
		return result;
	}

}
