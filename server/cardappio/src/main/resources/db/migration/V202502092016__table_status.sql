CREATE TABLE IF NOT EXISTS table_status (
    code NUMERIC(10) PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

INSERT INTO table_status (code, description) VALUES
(1, 'Livre'),
(2, 'Reservada'),
(3, 'Ocupada'),
(4, 'Indisponível');