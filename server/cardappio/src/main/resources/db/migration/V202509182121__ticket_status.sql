CREATE TABLE IF NOT EXISTS ticket_status (
    code NUMERIC(10) PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

INSERT INTO ticket_status (code, description) VALUES
(1, 'Aberta'),
(2, 'Finalizada'),
(3, 'Cancelada');
