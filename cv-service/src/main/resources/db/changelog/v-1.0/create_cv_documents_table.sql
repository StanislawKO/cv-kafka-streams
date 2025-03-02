CREATE TABLE cv_documents
(
    id              SERIAL PRIMARY KEY,
    description     VARCHAR(255),
    education       VARCHAR(255),
    employee_id     BIGINT NOT NULL,
    is_open_to_work BOOLEAN,
    linked_id       VARCHAR(255),
    work_experience VARCHAR(255)
);
