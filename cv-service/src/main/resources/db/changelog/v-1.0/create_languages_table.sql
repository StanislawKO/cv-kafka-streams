CREATE TABLE languages
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cv_id         BIGINT NOT NULL,
    language_name VARCHAR(255),

    CONSTRAINT fk_languages_id FOREIGN KEY (cv_id) REFERENCES cv_documents (id)
);
