package com.programacho.logingester;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogIngesterService {

    private final JdbcTemplate jdbcTemplate;

    public LogIngesterService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(LogIngesterPayload payload) {
        jdbcTemplate.update(
                "INSERT INTO exchange_log (application, type, direction, body) VALUES (?, ?, ?, ?)",
                payload.application(),
                payload.type(),
                payload.direction(),
                payload.body()
        );
    }
}
