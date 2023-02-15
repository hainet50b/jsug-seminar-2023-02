CREATE TABLE exchange_log (
    id INT AUTO_INCREMENT,
    application VARCHAR,
    type VARCHAR,
    direction VARCHAR,
    body VARCHAR,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
