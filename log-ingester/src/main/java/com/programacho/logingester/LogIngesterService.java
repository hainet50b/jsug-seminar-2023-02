package com.programacho.logingester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogIngesterService {

    private final Logger log = LoggerFactory.getLogger(LogIngesterService.class);

    private final JdbcTemplate jdbcTemplate;

    public LogIngesterService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void healthCheck() {
        log.info(jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", String.class));
    }
}
