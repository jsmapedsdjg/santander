package com.interview.test;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class PricingMessageParserTest {

    @Test
    public void shouldParseMessage() {
        Price price = PricingMessageParser.parse("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");

        assertEquals(price.getBid().compareTo(new BigDecimal("1.1000")),0 );
        assertEquals(price.getAsk().compareTo(new BigDecimal("1.2000")),0 );
        assertEquals("EUR/USD", price.getInstrument());
        LocalDate date = LocalDate.of(2020,6,1);
        LocalTime time = LocalTime.of(12,1,1,1000000);
        Assert.assertTrue(LocalDateTime.of(date,time).isEqual(price.getTimestamp()));
    }
}