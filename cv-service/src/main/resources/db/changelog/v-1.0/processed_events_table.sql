CREATE TABLE processed_events
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cv_id      VARCHAR(255),
    message_id VARCHAR(255)
);
