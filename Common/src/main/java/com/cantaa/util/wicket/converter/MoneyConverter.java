package com.cantaa.util.wicket.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.cantaa.util.type.Money;

/**
 * Converter for the Price-Type
 * @author Hans Lesmeister
 */
public class MoneyConverter extends AbstractAmountConverter<Money> {

    @Override
    protected Class<Money> getTargetType() {
        return Money.class;
    }

    @Override
    protected DecimalFormat getDecimalFormatInstance(Locale locale) {
        return (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
    }

    @Override
    protected Money createAmount(BigDecimal value) {
        return new Money(value);
    }
}
