package org.example.project.randomtimertolog;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.stereotype.Component;

@Component
public class RandomTimerToLog extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://foo?period=2000")
            .process(exchange -> {
                int delay = ThreadLocalRandom.current().nextInt(1000, 10000);
                Thread.sleep(delay);
            })
            .process(exchange -> {
                int failure = ThreadLocalRandom.current().nextInt(1, 10);
                if (failure == 1) {
                    throw new RuntimeException("Simulated random failure");
                }
            })
            .log("Execution succeeded at ${date:now}");
    }
}
