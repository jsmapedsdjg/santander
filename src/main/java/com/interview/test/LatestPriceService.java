package com.interview.test;

import com.interview.test.margin.MarginApplicator;

import java.util.Objects;
import java.util.Optional;

public class LatestPriceService {
    private final PricingMemoizer memoizer;
    private final MarginApplicator marginApplicator;

    public LatestPriceService(PricingMemoizer memoizer, MarginApplicator marginApplicator) {
        this.memoizer = Objects.requireNonNull(memoizer, "memoizer can't be null");
        this.marginApplicator = Objects.requireNonNull(marginApplicator, "marginApplicator can't be null");
    }

    public Optional<Price> priceForInstrument(String instrument) {
        return memoizer
                .get(instrument)
                .map(marginApplicator::applyMargins);
    }
}
