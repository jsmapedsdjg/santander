package com.interview.test;

import com.interview.test.margin.MarginApplicator;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;

public class Main {

    public static final BigDecimal DEFAULT_MARGIN = new BigDecimal("0.001");

    public static final void main(String... s) throws OperationsException, MBeanException {
        PricingMemoizer memoizer = new PricingMemoizer();
        PricingMessageConsumer pricingMessageConsumer = new PricingMessageConsumer(memoizer);

        MarginApplicator marginApplicator = new MarginApplicator(DEFAULT_MARGIN, DEFAULT_MARGIN);
        LatestPriceService latestPriceService = new LatestPriceService(memoizer, marginApplicator);
        LatestPriceController latestPriceController = new LatestPriceController(latestPriceService);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        mbs.registerMBean(latestPriceController, new ObjectName("com.interview.test:type=LatestPriceController"));
        mbs.registerMBean(pricingMessageConsumer, new ObjectName("com.interview.test:type=PricingMessageConsumer"));
        ethernalSleep();
    }

    private static void ethernalSleep() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException ignored) {

            }
        }
    }
}
