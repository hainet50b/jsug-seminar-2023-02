package com.programacho.logingester;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LogIngesterListener {

    private final LogIngesterService service;

    public LogIngesterListener(LogIngesterService service) {
        this.service = service;
    }

    @Bean
    public Consumer<LogIngesterPayload> log() {
        return service::insert;
    }
}
