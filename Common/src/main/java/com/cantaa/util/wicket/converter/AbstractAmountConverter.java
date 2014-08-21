package com.cantaa.util.wicket.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractDecimalConverter;
import org.apache.wicket.util.convert.converter.BigDecimalConverter;

import com.cantaa.util.type.Amount;


/**
 * Abstract Converter for Amounts
 * @author Hans Lesmeister
 */
public abstract class AbstractAmountConverter<T extends Amount<T>> extends AbstractDecimalConverter<T> {

    private BigDecimalConverter wrappedConverter;

    public AbstractAmountConverter() {
        wrappedConverter = new BigDecimalConverter();
    }

    @Override
    protected NumberFormat newNumberFormat(Locale locale) {
        DecimalFormat instance = getDecimalFormatInstance(locale);

        // Get rid of the Euro-Sign
        instance.setPositivePrefix("");
        instance.setPositiveSuffix("");
        instance.setNegativePrefix("-");
        instance.setNegativeSuffix("");
        instance.setMultiplier(1);

        return instance;
    }

    protected abstract DecimalFormat getDecimalFormatInstance(Locale locale);

    @Override
    public T convertToObject(String value, Locale locale) {
        return createAmount(wrappedConverter.convertToObject(value, locale));
    }

    protected abstract T createAmount(BigDecimal value);
}
