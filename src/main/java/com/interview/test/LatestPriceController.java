package com.interview.test;

import java.util.NoSuchElementException;
import java.util.Objects;

public class LatestPriceController implements LatestPriceControllerMBean {
    private final LatestPriceService latestPriceService;

    public LatestPriceController(LatestPriceService latestPriceService) {
        this.latestPriceService = Objects.requireNonNull(latestPriceService, "latestPriceService can't be null");
    }

    @Override
    public String getPrice(String instrument) {
        return latestPriceService.priceForInstrument(instrument)
                .orElseThrow(() -> new NoSuchElementException("We would return 404 in case of web-server"))
                .toString();
    }
}
