CREATE TABLE person_type (
    type VARCHAR(2) NOT NULL UNIQUE,
    description VARCHAR(20) NOT NULL
);

INSERT INTO person_type (type, description) VALUES
('PF', 'Pessoa Física'),
('PJ', 'Pessoa Jurídica');