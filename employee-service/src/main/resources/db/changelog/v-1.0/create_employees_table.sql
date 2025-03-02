CREATE TABLE employees
(
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    age bigint NOT NULL
);
