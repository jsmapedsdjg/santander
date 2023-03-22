package com.interview.test.margin;

import com.interview.test.Price;

import java.math.BigDecimal;
import java.util.Objects;

public class MarginApplicator {
    private final BigDecimal bidMargin;
    private final BigDecimal askMargin;

    public MarginApplicator(BigDecimal bidMargin, BigDecimal askMargin) {
        this.bidMargin = Objects.requireNonNull(bidMargin);
        this.askMargin = Objects.requireNonNull(askMargin);
    }

    public Price applyMargins(Price price) {
        return price.alter()
                .setBid(applyBidMargin(price.getBid()))
                .setAsk(applyAskMargin(price.getAsk()))
                .build();
    }

    private BigDecimal applyBidMargin(BigDecimal bid){
        return bid.subtract(bid.multiply(bidMargin));
    }

    private BigDecimal applyAskMargin(BigDecimal ask){
        return ask.add(ask.multiply(askMargin));
    }
}
