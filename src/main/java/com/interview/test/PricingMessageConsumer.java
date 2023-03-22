package com.interview.test;

import java.util.Objects;

public class PricingMessageConsumer implements PricingMessageConsumerMBean {
    private final PricingMemoizer memoizer;

    public PricingMessageConsumer(PricingMemoizer memoizer) {
        this.memoizer = Objects.requireNonNull(memoizer);
    }

    @Override
    public void onMessage(String message) {
        Price price = PricingMessageParser.parse(message);
        memoizer.append(price);
    }
}
