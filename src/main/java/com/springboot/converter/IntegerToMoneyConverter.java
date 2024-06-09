package com.springboot.converter;

import com.springboot.values.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class IntegerToMoneyConverter implements Converter<Integer, Money> {
    @Override
    public Money convert(Integer source) {
        return new Money(source);
    }
}
