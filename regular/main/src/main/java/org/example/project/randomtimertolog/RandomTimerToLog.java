package org.example.project.randomtimertolog;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.camel.builder.RouteBuilder;

public class RandomTimerToLog extends RouteBuilder {

    private static final int DEFAULT_RATE_PER_MINUTE = 60;
    private static final int DEFAULT_ERROR_PERCENTAGE = 10;

    @Override
    public void configure() throws Exception {
        int ratePerMinute = envInt("MESSAGE_RATE_PER_MINUTE", DEFAULT_RATE_PER_MINUTE);
        int errorPercentage = envInt("ERROR_PERCENTAGE", DEFAULT_ERROR_PERCENTAGE);
        long periodMs = 60_000L / Math.max(1, ratePerMinute);

        from("timer://foo?period=" + periodMs)
            .process(exchange -> {
                int roll = ThreadLocalRandom.current().nextInt(100); // 0..99
                if (roll < errorPercentage) {
                    throw new RuntimeException("Simulated random failure");
                }
            })
            .log("Execution succeeded at ${date:now}");
    }

    private static int envInt(String name, int defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }
}