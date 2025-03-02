CREATE TABLE certificates
(
    id               BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cv_id            BIGINT NOT NULL,
    certificate_name VARCHAR(255),

    CONSTRAINT fk_certificates_id FOREIGN KEY (cv_id) REFERENCES cv_documents (id)
);
