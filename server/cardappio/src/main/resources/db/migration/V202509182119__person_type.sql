CREATE TABLE person_type (
    code numeric(10) PRIMARY KEY,
    type VARCHAR(2) NOT NULL UNIQUE,
    description VARCHAR(20) NOT NULL
);

INSERT INTO person_type (code, type, description) VALUES
(1, 'PF', 'Pessoa Física'),
(2, 'PJ', 'Pessoa Jurídica');