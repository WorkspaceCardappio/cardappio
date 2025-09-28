CREATE TABLE IF NOT EXISTS table_status (
    code NUMERIC(10) NOT NULL,
    description VARCHAR(255) NOT NULL
);

INSERT INTO table_status (code, description) VALUES
(1, 'Livre'),
(2, 'Reserva'),
(3, 'Ocupada'),
(3, 'Indisponível');