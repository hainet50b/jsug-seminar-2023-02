package com.programacho.logingester;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogIngesterService {

    private final JdbcTemplate jdbcTemplate;

    private final ObservationRegistry observationRegistry;

    public LogIngesterService(JdbcTemplate jdbcTemplate, ObservationRegistry observationRegistry) {
        this.jdbcTemplate = jdbcTemplate;
        this.observationRegistry = observationRegistry;
    }

    public void insert(LogIngesterPayload payload) {
        Observation
                .createNotStarted("insert into exchange_log", observationRegistry)
                .observe(() -> jdbcTemplate.update(
                        "INSERT INTO exchange_log (application, type, direction, body) VALUES (?, ?, ?, ?)",
                        payload.application(),
                        payload.type(),
                        payload.direction(),
                        payload.body()
                ));
    }
}
