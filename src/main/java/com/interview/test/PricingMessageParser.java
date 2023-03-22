package com.interview.test;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PricingMessageParser {
    /**
     * message format:
     * 106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
     * id, instrument, bid, ask, timestamp
     */
    public static Price parse(String source) {
        Objects.requireNonNull(source, "source string can't be null");
        List<String> parts = Arrays.stream(source.split(",")).map(String::trim).toList();
        Validate.isTrue(parts.size() == 5, "malformed message format");
        LocalDateTime timestamp = LocalDateTime.parse(parts.get(4),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS"));
        return Price.builder()
                .setId(Long.parseLong(parts.get(0)))
                .setInstrument(parts.get(1))
                .setBid(new BigDecimal(parts.get(2)))
                .setAsk(new BigDecimal(parts.get(3)))
                .setTimestamp(timestamp)
                .build();
    }

}
