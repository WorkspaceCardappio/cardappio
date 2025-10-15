CREATE TABLE IF NOT EXISTS unity_of_measurement (
    code NUMERIC(10) PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

INSERT INTO unity_of_measurement (code, description) VALUES
(1, 'Litro'),
(2, 'Mililitro'),
(3, 'Grama'),
(4, 'Quilograma'),
(5, 'Unidade');