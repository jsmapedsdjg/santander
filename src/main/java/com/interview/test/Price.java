package com.interview.test;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price {
    private final long id;
    private final String instrument;
    private final BigDecimal ask;
    private final BigDecimal bid;
    private final LocalDateTime timestamp;


    private Price(long id, String instrument, BigDecimal bid, BigDecimal ask, LocalDateTime timestamp) {
        this.id = id;
        this.instrument = Objects.requireNonNull(instrument, "instrument can't be null");
        Validate.isTrue(bid.compareTo(BigDecimal.ZERO) > 0, "bid must be greater than zero");
        Validate.isTrue(ask.compareTo(BigDecimal.ZERO) > 0, "ask must be greater than zero");
        Validate.isTrue(ask.compareTo(bid) > 0, "ask price must be greater or equal bid");
        this.ask = ask;
        this.bid = bid;
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp can't be null");
    }

    public boolean isAfter(Price other) {
        return this.getTimestamp().isAfter(other.getTimestamp());
    }


    public long getId() {
        return id;
    }

    public String getInstrument() {
        return instrument;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public Builder alter() {
        return new Builder()
                .setId(id)
                .setInstrument(instrument)
                .setAsk(ask)
                .setBid(bid)
                .setTimestamp(timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String instrument;
        private BigDecimal ask;
        private BigDecimal bid;
        private LocalDateTime timestamp;

        public Builder() {

        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setInstrument(String instrument) {
            this.instrument = instrument;
            return this;
        }

        public Builder setAsk(BigDecimal ask) {
            this.ask = ask;
            return this;
        }

        public Builder setBid(BigDecimal bid) {
            this.bid = bid;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Price build() {
            return new Price(id, instrument, bid, ask, timestamp);
        }
    }


    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", instrument='" + instrument + '\'' +
                ", ask=" + ask +
                ", bid=" + bid +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        //equals-hashcode contract is sound, unless ask,bid or timestamp are added to hashcode
        return id == price.id
                && Objects.equals(instrument, price.instrument)
                && ask.compareTo(price.ask) == 0
                && bid.compareTo(price.bid) == 0
                && timestamp.isEqual(price.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instrument);
    }
}
