INSERT INTO cv_documents (description, education, employee_id, is_open_to_work, linked_id, work_experience)
VALUES ('I am top programmer', 'MIT', 1, TRUE, 'linkedId', 'I have been working for 5 years');

INSERT INTO certificates (cv_id, certificate_name)
VALUES (1, 'Certificate 1'),
       (1, 'Certificate 2');

INSERT INTO languages (cv_id, language_name)
VALUES (1, 'Russian'),
       (1, 'English');

INSERT INTO skills (cv_id, skill_name)
VALUES (1, 'Java'),
       (1, 'Python');
