CREATE TABLE skills
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    cv_id      BIGINT NOT NULL,
    skill_name VARCHAR(255),

    CONSTRAINT fk_skills_id FOREIGN KEY (cv_id) REFERENCES cv_documents (id)
);
