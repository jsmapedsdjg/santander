package com.interview.test;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PricingMemoizer {
    public ConcurrentMap<String, Price> instrumentToPrice = new ConcurrentHashMap<>();

    public void append(Price newPrice) {
        //thread-safe due to disregard of the order of operations only latest value is stored
        instrumentToPrice.compute(newPrice.getInstrument(), (s, current) -> {
            boolean shouldReplace = current == null || newPrice.isAfter(current);
            return shouldReplace ? newPrice : current;
        });
    }

    public Optional<Price> get(String instrument) {
        return Optional.ofNullable(instrumentToPrice.get(instrument));
    }
}
