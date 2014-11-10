package com.cantaa.util.wicket.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.cantaa.util.type.Percentage;

/**
 * @author Hans Lesmeister
 */
public class PercentageConverter extends AbstractAmountConverter<Percentage> {

    @Override
    protected Class<Percentage> getTargetType() {
        return Percentage.class;
    }

    @Override
    protected DecimalFormat getDecimalFormatInstance(Locale locale) {
        return (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
    }

    @Override
    protected Percentage createAmount(BigDecimal value) {
        return new Percentage(value);
    }
}
