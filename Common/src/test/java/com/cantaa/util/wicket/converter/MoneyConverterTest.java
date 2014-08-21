package com.cantaa.util.wicket.converter;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.cantaa.util.type.Money;

/**
 * @author Hans Lesmeister
 */
public class MoneyConverterTest {

    @Test
    public void testConvertToString() throws Exception {

        MoneyConverter converter = new MoneyConverter();

        Money money = new Money("9.5");
        String stringMoney = converter.convertToString(money, Locale.GERMANY);
        assertEquals("9,50", stringMoney);

        assertEquals("1.500,00", converter.convertToString(new Money("1500"), Locale.GERMANY));
    }
}
