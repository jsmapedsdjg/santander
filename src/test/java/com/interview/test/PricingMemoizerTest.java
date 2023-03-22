package com.interview.test;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class PricingMemoizerTest {
    public static final String INSTRUMENT = "EUR/USD";
    public static final String OTHER_INSTRUMENT = "HKD/USD";
    public static final BigDecimal ANY_BID = new BigDecimal("1.1000");
    public static final BigDecimal ANY_ASK = new BigDecimal("1.2000");
    private static final LocalDate DATE = LocalDate.of(2020,6,1);
    private static final LocalTime OLD_TIME =LocalTime.of(12,1,1,1000000);
    private static final LocalTime NEW_TIME =LocalTime.of(12,1,1,1000001);
    private static final LocalDateTime OLD_DATETIME = LocalDateTime.of(DATE, OLD_TIME);
    private static final LocalDateTime NEW_DATETIME = LocalDateTime.of(DATE, NEW_TIME);

    private PricingMemoizer memoizer = new PricingMemoizer();

    private void setup() {
        memoizer = new PricingMemoizer();
    }

    @Test
    public void shouldStoreSingleValue() {
        Price price = Price.builder()
                .setId(123)
                .setInstrument(INSTRUMENT)
                .setBid(ANY_BID)
                .setAsk(ANY_ASK)
                .setTimestamp(OLD_DATETIME)
                .build();


        memoizer.append(price);

        Assert.assertEquals(memoizer.get(INSTRUMENT), Optional.of(price));
    }

    @Test
    public void shouldReplaceOldValueWithNew() {
        Price oldPrice = Price.builder()
                .setId(123)
                .setInstrument(INSTRUMENT)
                .setBid(ANY_BID)
                .setAsk(ANY_ASK)
                .setTimestamp(OLD_DATETIME)
                .build();
        Price newPrice = Price.builder()
                .setId(124)
                .setInstrument(INSTRUMENT)
                .setBid(ANY_BID)
                .setAsk(ANY_ASK)
                .setTimestamp(NEW_DATETIME)
                .build();

        memoizer.append(oldPrice);
        memoizer.append(newPrice);

        Assert.assertEquals(memoizer.get(INSTRUMENT), Optional.of(newPrice));
    }

    @Test
    public void shouldNotReplaceNewValueWithOld() {
        Price oldPrice = Price.builder()
                .setId(123)
                .setInstrument(INSTRUMENT)
                .setBid(ANY_BID)
                .setAsk(ANY_ASK)
                .setTimestamp(OLD_DATETIME)
                .build();;
        Price newPrice = Price.builder()
                .setId(124)
                .setInstrument(INSTRUMENT)
                .setBid(ANY_BID)
                .setAsk(ANY_ASK)
                .setTimestamp(NEW_DATETIME)
                .build();;

        memoizer.append(newPrice);
        memoizer.append(oldPrice);

        Assert.assertEquals(memoizer.get(INSTRUMENT), Optional.of(newPrice));
    }


    @Test
    public void shouldStoreDifferentInstrumentsIndependently() {
        Price price = Price.builder()
                .setId(123)
                .setInstrument(INSTRUMENT)
                .setBid(ANY_BID)
                .setAsk(ANY_ASK)
                .setTimestamp(OLD_DATETIME)
                .build();;
        Price otherPrice = Price.builder()
                .setId(124)
                .setInstrument(OTHER_INSTRUMENT)
                .setBid(ANY_BID)
                .setAsk(ANY_ASK)
                .setTimestamp(NEW_DATETIME)
                .build();;

        memoizer.append(price);
        memoizer.append(otherPrice);

        Assert.assertEquals(memoizer.get(INSTRUMENT), Optional.of(price));
        Assert.assertEquals(memoizer.get(OTHER_INSTRUMENT), Optional.of(otherPrice));
    }

}